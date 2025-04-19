// ======================= CONFIGURACIÓN =======================
import { urlBase } from '/FrontEnd/js/constante.js';

const EVENTO_API_BASE_URL    = `${urlBase}events/`;
const CATEGORIA_API_BASE_URL = `${urlBase}category-events/`;

document.addEventListener("DOMContentLoaded", function () {
    const form              = document.getElementById("evento-form");
    const formEditar        = document.getElementById("form-editar-evento");
    const container         = document.getElementById("eventos-container");
    const seccionCrear      = document.getElementById("addEventSection");
    const modalEditar       = document.getElementById("modal-editar-evento");
    const selectEventoCat   = document.getElementById("evento-categoria");
    const selectFilterCat   = document.getElementById("filter-categoria");
    const filterForm        = document.getElementById("filter-form");
    const selectEditarCat   = document.getElementById("editar-categoria"); // Modal categoría

    // ======================= AUXILIAR: POBLAR SELECTS =======================
    function poblarSelects(categorias) {
        [selectEventoCat, selectFilterCat, selectEditarCat].forEach(select => {
            select.innerHTML = `<option value="">— Selecciona una categoría —</option>`;
            categorias.forEach(cat => {
                const opt = document.createElement("option");
                opt.value       = cat.category_id;
                opt.textContent = cat.name;
                select.appendChild(opt);
            });
        });
    }

    // ======================= CARGAR CATEGORÍAS =======================
    async function cargarCategorias() {
        try {
            const resp = await fetch(CATEGORIA_API_BASE_URL);
            if (!resp.ok) throw new Error();
            const categorias = await resp.json();
            poblarSelects(categorias);
            return;
        } catch {
            console.warn("⚠️ No cargó categorías desde /categories/, extrayendo de eventos...");
        }

        // Fallback: extraer categorías de los propios eventos
        try {
            const r = await fetch(EVENTO_API_BASE_URL);
            if (!r.ok) throw new Error();
            const eventos = await r.json();
            const mapa = new Map();
            eventos.forEach(evt => {
                const cat = evt.categoryEvent;
                if (cat && !mapa.has(cat.category_id)) {
                    mapa.set(cat.category_id, cat.name);
                }
            });
            const categorias = Array.from(mapa, ([id, name]) => ({ category_id: id, name }));
            poblarSelects(categorias);
        } catch (e) {
            console.error("❌ Error extrayendo categorías de eventos:", e);
        }
    }

    // ======================= CREAR TARJETA =======================
    function formatDateForDisplay(dateString) {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return `${day}/${month}/${year}`; // Formato dd/mm/yyyy
    }

    function crearTarjetaEvento(evento) {
        if (!evento.idEvent) return console.error("ID de evento no encontrado:", evento);

        const catName = evento.categoryEvent?.name || "Categoría no disponible";
        const card = document.createElement("div");
        card.classList.add("evento-card");
        card.dataset.id = evento.idEvent;
        card.innerHTML = `
            <div class="evento-actions">
                <button class="btn-edit"><i class="fas fa-edit"></i></button>
                <button class="btn-delete"><i class="fas fa-trash-alt"></i></button>
            </div>
            <img src="${evento.imageUrl}" alt="Imagen del evento">
            <div class="evento-info">
                <h3>${evento.eventName}</h3>
                <p class="descripcion">${evento.description}</p>
                <p class="ubicacion">📍 ${evento.location}</p>
                <p class="categoria">🎫 Categoría: ${catName}</p>
                <p class="fecha">📅 Fecha: ${formatDateForDisplay(evento.date)}</p> <!-- Fecha formateada -->
            </div>
        `;

        // Eliminar
        card.querySelector(".btn-delete").addEventListener("click", async () => {
            const id = card.dataset.id;
            if (!id) return alert("❌ ID no encontrado.");
            if (!confirm("¿Eliminar este evento?")) return;
            try {
                const resp = await fetch(`${EVENTO_API_BASE_URL}${id}`, { method: "DELETE" });
                if (!resp.ok) throw new Error();
                card.remove();
                alert("✅ Evento eliminado");
            } catch {
                alert("❌ Error al eliminar");
            }
        });

        // Editar
        card.querySelector(".btn-edit").addEventListener("click", () => {
            document.getElementById("editar-id").value = evento.idEvent;
            document.getElementById("editar-nombre").value = evento.eventName;
            document.getElementById("editar-descripcion").value = evento.description;
            document.getElementById("editar-ubicacion").value = evento.location;
            document.getElementById("editar-categoria").value = evento.categoryEvent.category_id;
            document.getElementById("editar-imagen").value = evento.imageUrl;
            modalEditar.style.display = "block";
        });

        container.appendChild(card);
    }

    // ======================= CARGAR EVENTOS =======================
    async function cargarEventos(filtros = {}) {
        try {
            const qs  = new URLSearchParams(filtros).toString();
            const url = qs ? `${EVENTO_API_BASE_URL}filter?${qs}` : EVENTO_API_BASE_URL;
            const resp = await fetch(url);
            if (!resp.ok) throw new Error();
            const eventos = await resp.json();
            container.innerHTML = "";
            eventos.forEach(crearTarjetaEvento);
        } catch (e) {
            console.error("❌ Error al cargar eventos:", e);
        }
    }

    // ======================= FILTRAR EVENTOS =======================
    function convertToISODate(dateString) {
        const [day, month, year] = dateString.split('/');
        if (day && month && year) {
            return `${year}-${month}-${day}`; // Convierte a yyyy-mm-dd
        }
        return null; // Si la fecha no está bien, no la usamos
    }

    filterForm.addEventListener("submit", e => {
        e.preventDefault();

        const filtros = {};

        const fn  = document.getElementById("filter-nombre").value.trim();
        const loc = document.getElementById("filter-ubicacion").value.trim();
        const dt  = document.getElementById("filter-fecha").value.trim();
        const cat = selectFilterCat.options[selectFilterCat.selectedIndex].text.trim();

        if (fn) filtros.event_name = fn;
        if (loc) filtros.location = loc;

        if (dt) {
            const formattedDate = convertToISODate(dt);
            if (formattedDate) {
                filtros.date = formattedDate;
            }
        }

        // ⚠️ Solo agregar categoría si no es la opción por defecto
        if (cat && cat !== "— Selecciona una categoría —") {
            filtros.category_name = cat;
        }

        console.log(filtros);
        cargarEventos(filtros);
    });

    // ======================= CREAR EVENTO =======================
    form.addEventListener("submit", async e => {
        e.preventDefault();
        const btn = document.getElementById("submit-btn");
        btn.disabled = true;

        const eventName   = document.getElementById("evento-nombre").value.trim();
        const description = document.getElementById("evento-descripcion").value.trim();
        const location    = document.getElementById("evento-ubicacion").value.trim();
        const categoryId  = selectEventoCat.value;
        const imageUrl    = document.getElementById("evento-imagen").value.trim();

        if (!eventName || !description || !location || !imageUrl || !categoryId) {
            alert("Todos los campos son obligatorios y debes elegir una categoría.");
            btn.disabled = false;
            return;
        }

        const nuevoEvento = {
            eventName,
            description,
            location,
            categoryId: parseInt(categoryId),
            imageUrl
        };

        try {
            const resp = await fetch(EVENTO_API_BASE_URL, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(nuevoEvento)
            });
            if (!resp.ok) throw new Error();
            form.reset();
            alert("✅ Evento registrado con éxito");
            seccionCrear.style.display = "none";
            cargarEventos();
        } catch {
            alert("❌ Error al registrar el evento.");
        } finally {
            btn.disabled = false;
        }
    });

    // ======================= ACTUALIZAR EVENTO =======================
    formEditar.addEventListener("submit", async e => {
        e.preventDefault();
    
        const btn = document.getElementById("submit-btn-editar");
    
        // Verificar si el botón de editar existe
        if (btn) {
            btn.disabled = true;
        } else {
            console.error("No se encontró el botón de actualización.");
            return;
        }
    
        // Recoger los valores del formulario de edición
        const id          = document.getElementById("editar-id").value.trim();
        const eventName   = document.getElementById("editar-nombre").value.trim();
        const description = document.getElementById("editar-descripcion").value.trim();
        const location    = document.getElementById("editar-ubicacion").value.trim();
        const categoryId  = document.getElementById("editar-categoria").value;
        const imageUrl    = document.getElementById("editar-imagen").value.trim();
    
        // Validar que todos los campos estén completos
        if (!eventName || !description || !location || !imageUrl || !categoryId) {
            alert("Todos los campos son obligatorios y debes elegir una categoría.");
            if (btn) btn.disabled = false;
            return;
        }
    
        // Crear el objeto con los datos del evento a actualizar
        const eventoActualizado = {
            eventName,
            description,
            location,
            categoryId: parseInt(categoryId),
            imageUrl
        };
    
        try {
            // Enviar la solicitud PUT al servidor para actualizar el evento
            const resp = await fetch(`${EVENTO_API_BASE_URL}${id}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(eventoActualizado)
            });
    
            // Verificar si la respuesta fue exitosa
            if (!resp.ok) throw new Error();
    
            // Si todo fue bien, mostrar un mensaje de éxito
            alert("✅ Evento actualizado con éxito");
    
            // Cerrar el modal de edición
            modalEditar.style.display = "none";
    
            // Recargar la lista de eventos
            cargarEventos();
    
        } catch (error) {
            // Si ocurre un error, mostrar un mensaje de error
            alert("❌ Error al actualizar el evento.");
            console.error(error);
        } finally {
            // Habilitar el botón de edición nuevamente
            if (btn) btn.disabled = false;
        }
    });

    cargarCategorias();
    cargarEventos();
});
