document.addEventListener("DOMContentLoaded", function () {
    // Obtener la sección de "Agregar Evento" y los botones
    const seccionCrearEvento = document.getElementById("addEventSection");
    const btnAddEvent = document.getElementById("addEventBtn");
    const btnCancelEvent = document.getElementById("cancelEventBtn");
  
    // Ocultar la sección de crear evento al cargar la página
    if (seccionCrearEvento) {
      seccionCrearEvento.style.display = "none"; // Inicialmente oculta
    }
  
    // Mostrar la sección de crear evento cuando se hace clic en el botón "Agregar Evento"
    if (btnAddEvent) {
      btnAddEvent.addEventListener("click", function () {
        if (seccionCrearEvento) {
          seccionCrearEvento.style.display = "block"; // Mostrar formulario
        }
      });
    }
  
    // Ocultar la sección de crear evento cuando se hace clic en el botón "Cancelar"
    if (btnCancelEvent) {
      btnCancelEvent.addEventListener("click", function () {
        if (seccionCrearEvento) {
          seccionCrearEvento.style.display = "none"; // Ocultar formulario
        }
      });
    }
  
    // Manejo del formulario (opcional: podrías hacer validaciones y/o enviar datos aquí)
    const form = document.getElementById("addEventForm");
    if (form) {
      form.addEventListener("submit", function (event) {
        event.preventDefault();
        const eventName = document.getElementById("eventName").value;
        const eventDescription = document.getElementById("eventDescription").value;
        const eventImageUrl = document.getElementById("eventImageUrl").value;
  
        // Aquí puedes agregar lógica para guardar los datos del evento (por ejemplo, enviar a un servidor)
        console.log("Evento guardado:", {
          eventName,
          eventDescription,
          eventImageUrl,
        });
  
        // Después de guardar, ocultar el formulario
        if (seccionCrearEvento) {
          seccionCrearEvento.style.display = "none"; // Ocultar formulario
        }
      });
    }
  });

import { urlBase } from '/FrontEnd/Js/constante.js'; 

// Definir la URL base para la API de asistentes
// Suponiendo que ya tienes la URL de la API y un contenedor para los eventos
const EVENTO_API_BASE_URL = `${urlBase}events/`; // Usa la URL base de tu API

// Función para cargar eventos desde la API y mostrarlos en la galería
async function cargarEventosDestacados() {
    const container = document.getElementById("galleryContainer");
    
    if (!container) {
        console.error("❌ El contenedor 'galleryContainer' no se encuentra en el DOM.");
        return;
    }
    
    try {
        const resp = await fetch(EVENTO_API_BASE_URL); // Cambia esta URL según sea necesario
        if (!resp.ok) throw new Error('Error al cargar eventos.');

        const eventos = await resp.json();
        container.innerHTML = ""; // Limpiar el contenedor antes de agregar nuevos elementos

        // Limitar a los primeros 6 eventos
        eventos.slice(0, 6).forEach(evento => {
            const item = document.createElement("div");
            item.classList.add("gallery-item");
            item.onclick = () => showProjectDetails(evento.idEvent); // Mostrar detalles al hacer clic

            item.innerHTML = `
                <img src="${evento.imageUrl}" alt="${evento.eventName}">
                <div class="overlay">
                    <span>Ver detalles</span>
                </div>
            `;
            container.appendChild(item);
        });
    } catch (error) {
        console.error("❌ Error al cargar eventos destacados:", error);
    }
}

// Llamar a la función para cargar los eventos cuando el DOM esté listo
document.addEventListener("DOMContentLoaded", function () {
    cargarEventosDestacados();
});


// Llamar a la función para cargar los eventos destacados
cargarEventosDestacados();

  
  