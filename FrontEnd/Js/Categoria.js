import { urlBase } from '/FrontEnd/Js/constante.js'; // Importar la URL base desde 'constante.js'

// Concatenar la URL base con la ruta específica para los eventos por categoría
const API_URL_CATEGORIA = `${urlBase}category-events/`;

console.log(API_URL_CATEGORIA); // Verifica que la URL esté correctamente formada

// Registrar categoría
document.getElementById("registro-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let bodyContent = JSON.stringify({
        "name": document.getElementById("registro-nombre").value,
        "description": document.getElementById("registro-descripcion").value
    });

    let response = await fetch(API_URL_CATEGORIA, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: bodyContent
    });

    let data = await response.text();
    console.log(data);
    alert("Categoría de evento registrada con éxito");
});

// ======================= ACTUALIZAR CATEGORÍA =======================
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("categoria-update-form").addEventListener("submit", async function (event) {
        event.preventDefault();

        let id = document.getElementById("categoria-id-update").value.trim();
        let nombre = document.getElementById("categoria-nombre-update").value.trim();
        let descripcion = document.getElementById("categoria-descripcion-update").value.trim();

        let mensajeElemento = document.getElementById("mensaje");
        mensajeElemento.innerText = "";

        if (!id || !nombre || !descripcion) {
            let mensaje = "Todos los campos son obligatorios ⚠️";
            mensajeElemento.innerText = mensaje;
            alert(mensaje);
            return;
        }

        let bodyContent = JSON.stringify({
            "name": nombre,
            "description": descripcion
        });

        let url = `${API_URL_CATEGORIA}${id}`;

        try {
            let response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Accept": "*/*",
                    "Content-Type": "application/json"
                },
                body: bodyContent
            });

            let data = await response.json();
            console.log("📢 Respuesta del servidor:", data);

            if (response.ok) {
                let mensaje = "Categoría actualizada con éxito 🎉";
                mensajeElemento.innerText = mensaje;
                alert(mensaje);
            } else {
                let mensaje = `Error: ${data.message || "No se pudo actualizar la categoría"}`;
                mensajeElemento.innerText = mensaje;
                alert(mensaje);
            }

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            let mensaje = "Error de conexión 😢";
            mensajeElemento.innerText = mensaje;
            alert(mensaje);
        }
    });
});

// Buscar Categoría
document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("searchInputCategory");
    const button = document.getElementById("searchButtonCategory");
    const tableBody = document.getElementById("tableBodyCategory");

    button.addEventListener("click", async function () {
        const query = input.value.trim().toLowerCase();
        let url = new URL(`${API_URL_CATEGORIA}filter`);

        if (!query) return;

        if (query === "activo") {
            url.searchParams.append("status", true);
        } else if (query === "inactivo") {
            url.searchParams.append("status", false);
        } else if (!isNaN(query)) {
            url.searchParams.append("id", query);
        } else if (query.length < 30) {
            url.searchParams.append("name", query);
        } else {
            url.searchParams.append("description", query);
        }

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("❌ Error al obtener categorías");

            const data = await response.json();

            if (data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="5">😕 No se encontraron categorías</td></tr>`;
                return;
            }

            tableBody.innerHTML = data.map(cat => `
                <tr>
                    <td>${cat.category_id ?? '—'}</td>
                    <td>${cat.name ?? '—'}</td>
                    <td>${cat.description || '—'}</td>
                    <td>${cat.status ? 'Activo' : 'Inactivo'}</td>
                    <td>
                        <button class="edit-btn" data-id="${cat.category_id}">✏️</button>
                        <button class="delete-btn" data-id="${cat.category_id}">🗑️</button>
                    </td>
                </tr>
            `).join("");
        } catch (error) {
            console.error(error);
            alert("🚨 No se pudo filtrar la lista de categorías.");
        }
    });
});

// Eliminar Categoría
document.getElementById("categoria-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevenir envío del formulario

    const id = document.getElementById("categoria-id").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de que este elemento exista en tu HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${API_URL_CATEGORIA}${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Categoría con ID ${id} eliminada correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("categoria-delete-form").reset();
            alert("✅ Categoría eliminada correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar la categoría"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
