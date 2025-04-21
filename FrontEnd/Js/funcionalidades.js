import { urlBase } from '/FrontEnd/Js/constante.js'; 

// ✅ Variables API
const EVENTO_API_BASE_URL = `${urlBase}events/`;
const ORGANIZER_API_BASE_URL = `${urlBase}organizer/`;

// ✅ Función para cargar eventos destacados
async function cargarEventosDestacados() {
  const container = document.getElementById("galleryContainer");

  if (!container) {
    console.warn("⚠️ Contenedor 'galleryContainer' no encontrado en el DOM.");
    return;
  }

  try {
    const resp = await fetch(EVENTO_API_BASE_URL);
    if (!resp.ok) throw new Error("Error al cargar eventos.");

    const eventos = await resp.json();
    container.innerHTML = "";

    eventos.slice(0, 6).forEach(evento => {
      const item = document.createElement("div");
      item.classList.add("gallery-item");
      item.onclick = () => showProjectDetails(evento.idEvent);

      item.innerHTML = `
        <img src="${evento.imageUrl}" alt="${evento.eventName}">
        <div class="overlay"><span>Ver detalles</span></div>
      `;
      container.appendChild(item);
    });

  } catch (error) {
    console.error("❌ Error al cargar eventos:", error);
  }
}

// ✅ Función para cargar organizadores (limitado o completo)
async function cargarOrganizadores(mostrarTodos = false) {
  const container = document.getElementById("organizerList");

  if (!container) {
    console.warn("⚠️ Contenedor 'organizerList' no encontrado en el DOM.");
    return;
  }

  try {
    const resp = await fetch(ORGANIZER_API_BASE_URL);
    if (!resp.ok) throw new Error("Error al cargar organizadores.");

    const organizadores = await resp.json();
    container.innerHTML = "";

    const lista = mostrarTodos ? organizadores : organizadores.slice(0, 6);

    lista.forEach(org => {
      const item = document.createElement("div");
      item.classList.add("organizer-card");

      item.innerHTML = `
        <img src="${org.imageUrl || 'images/default-avatar.png'}" alt="${org.name}" class="organizer-avatar">
        <div class="organizer-details">
          <h3>${org.name}</h3>
          <p><strong>📞 Teléfono:</strong> ${org.phone}</p>
          <p><strong>✉️ Correo:</strong> ${org.email}</p>
        </div>
      `;
      container.appendChild(item);
    });

    // Mostrar u ocultar botón según el estado
    const btnVerTodos = document.getElementById("verTodosBtn");
    if (btnVerTodos) {
      btnVerTodos.style.display = mostrarTodos || organizadores.length <= 6 ? "none" : "block";
    }

  } catch (error) {
    console.error("❌ Error al cargar organizadores:", error);
  }
}

// ✅ Manejo de sección "Agregar Evento"
document.addEventListener("DOMContentLoaded", function () {
  const seccionCrearEvento = document.getElementById("addEventSection");
  const btnAddEvent = document.getElementById("addEventBtn");
  const btnCancelEvent = document.getElementById("cancelEventBtn");

  if (seccionCrearEvento) seccionCrearEvento.style.display = "none";

  if (btnAddEvent) {
    btnAddEvent.addEventListener("click", () => {
      seccionCrearEvento.style.display = "block";
    });
  }

  if (btnCancelEvent) {
    btnCancelEvent.addEventListener("click", () => {
      seccionCrearEvento.style.display = "none";
    });
  }

  const form = document.getElementById("addEventForm");
  if (form) {
    form.addEventListener("submit", function (event) {
      event.preventDefault();
      const eventName = document.getElementById("eventName").value;
      const eventDescription = document.getElementById("eventDescription").value;
      const eventImageUrl = document.getElementById("eventImageUrl").value;

      console.log("Evento guardado:", { eventName, eventDescription, eventImageUrl });
      seccionCrearEvento.style.display = "none";
    });
  }

  // ✅ Botón para ver todos los organizadores
  const btnVerTodos = document.getElementById("verTodosBtn");
  if (btnVerTodos) {
    btnVerTodos.addEventListener("click", () => cargarOrganizadores(true));
  }

  // ✅ Cargar datos al iniciar
  cargarEventosDestacados();
  cargarOrganizadores(); // solo 6 por defecto
});
