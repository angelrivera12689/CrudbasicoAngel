// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/js/constante.js';

// Definir la URL base para eventos
const EVENTO_API_BASE_URL = `${urlBase}events/`;
// Definir la URL base para los asistentes con filtro
const ASISTENTE_API_BASE_URL = `${urlBase}assistants/filter`; // API base para los asistentes
// Definir la URL base para las reseñas
const RESEÑA_API_BASE_URL = `${urlBase}reviews/`; // API base para las reseñas

document.addEventListener("DOMContentLoaded", () => {
  // Obtener el ID del evento desde la URL
  const params = new URLSearchParams(window.location.search);
  const eventoId = params.get("id");

  if (!eventoId) {
    return console.error("No se proporcionó ID de evento en la URL");
  }

  // Cargar los detalles del evento
  cargarDetallesEvento(eventoId);

  // Cargar las reseñas del evento
  cargarReseñasEvento(eventoId);

  // Elementos del DOM para abrir/cerrar el modal
  const openResenaBtn = document.querySelector('.btn-outline-primary');
  const resenaModal = document.getElementById('resenaModal');
  const closeResenaBtn = document.getElementById('closeResenaBtn');
  const resenaForm = document.getElementById('review-form');
  const eventIdInput = document.getElementById('review-event-id');
  const assistantNameInput = document.getElementById('review-assistant-name');  // Nuevo campo para el nombre del asistente

  // Preasignar el ID del evento al input oculto
  if (eventIdInput) {
    eventIdInput.value = eventoId;
  }

  // Abrir el modal de reseña
  if (openResenaBtn) {
    openResenaBtn.addEventListener('click', () => {
      resenaModal.style.display = 'flex';
      window.scrollTo(0, document.body.scrollHeight); // Desplazar hacia abajo cuando el modal se abre
    });
  }

  // Cerrar el modal con el botón "×"
  if (closeResenaBtn) {
    closeResenaBtn.addEventListener('click', () => {
      resenaModal.style.display = 'none';
    });
  }

  // Cerrar el modal al hacer clic fuera del modal
  if (resenaModal) {
    resenaModal.addEventListener('click', e => {
      if (e.target === resenaModal) {
        resenaModal.style.display = 'none';
      }
    });
  }

  // Manejo del envío del formulario de reseña
  if (resenaForm) {
    resenaForm.addEventListener('submit', async e => {
      e.preventDefault();  // Evitar que la página se recargue

      // Obtener el nombre del asistente desde el input
      const assistantName = assistantNameInput.value.trim();

      if (!assistantName) {
        alert("El nombre del asistente es obligatorio.");
        return;
      }

      try {
        // Consultar la base de datos para obtener el assistantId basado en el nombre
        const assistant = await consultarAsistentePorNombre(assistantName);

        if (!assistant) {
          alert("No se encontró un asistente con ese nombre.");
          return;
        }

        // Crear el objeto con los datos de la reseña
        const reviewData = {
          comment: document.getElementById('review-comment').value.trim(),
          rating: parseInt(document.getElementById('review-rating').value),
          eventId: parseInt(eventoId), // Usamos el eventoId ya preasignado
          assistantName: assistant.name // Usamos el nombre del asistente
        };

        // Validación de los campos
        if (!reviewData.comment || isNaN(reviewData.rating) || isNaN(reviewData.eventId) || !reviewData.assistantName) {
          alert("Todos los campos son obligatorios y deben ser válidos.");
          return;
        }

        // Realizar la solicitud POST para registrar la reseña
        const response = await fetch(RESEÑA_API_BASE_URL, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(reviewData)
        });

        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

        // Mostrar mensaje de éxito
        alert('¡Reseña enviada con éxito!');
        resenaModal.style.display = 'none';
        resenaForm.reset();  // Limpiar el formulario después de enviar
      } catch (error) {
        console.error('Error al enviar la reseña:', error);
        alert('❌ Error al enviar la reseña. Intenta de nuevo.');
      }
    });
  }
});

// Función para formatear la fecha de manera amigable (DD/MM/YYYY)
function formatDateForDisplay(dateString) {
  const d = new Date(dateString);
  return [
    String(d.getDate()).padStart(2, "0"),
    String(d.getMonth() + 1).padStart(2, "0"),
    d.getFullYear()
  ].join("/");
}

// Función para cargar los detalles del evento
async function cargarDetallesEvento(eventoId) {
  try {
    const resp = await fetch(`${EVENTO_API_BASE_URL}${eventoId}`);
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const evento = await resp.json();

    // Llenar el DOM con la información del evento
    document.getElementById("evento-imagen").src = evento.imageUrl || "/FrontEnd/img/default.jpg";
    document.getElementById("evento-nombre").textContent = evento.eventName;
    document.getElementById("evento-descripcion").textContent = evento.description;
    document.getElementById("evento-fecha").textContent = `📅 ${formatDateForDisplay(evento.date)}`;
    document.getElementById("evento-ubicacion").textContent = `📍 ${evento.location}`;
    document.getElementById("evento-categoria").textContent = `🎫 ${evento.categoryEvent?.name || "No disponible"}`;
    document.getElementById("evento-mas-info").textContent = evento.additionalInfo || "No hay información adicional disponible.";
  } catch (err) {
    console.error("Error cargando detalles:", err);
    alert("❌ No se pudo cargar los detalles del evento.");
  }
}

// Función para cargar las reseñas del evento
async function cargarReseñasEvento(eventoId) {
  try {
    const resp = await fetch(`${RESEÑA_API_BASE_URL}?eventId=${eventoId}`);
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const reseñas = await resp.json();

    // Obtener el contenedor donde se mostrarán las reseñas
    const reseñasContainer = document.getElementById("reseñas-container");
    reseñasContainer.innerHTML = "";  // Limpiar el contenedor antes de agregar nuevas reseñas

    // Verificar si hay reseñas
    if (reseñas.length === 0) {
      reseñasContainer.innerHTML = "<p>No hay reseñas para este evento aún.</p>";
      return;
    }

    // Agregar cada reseña al contenedor
    reseñas.forEach(reseña => {
      const reseñaDiv = document.createElement("div");
      reseñaDiv.classList.add("reseña", "reseña-style"); // Añadimos una clase extra para los estilos

      // Crear el contenido de la reseña
      reseñaDiv.innerHTML = `
        <div class="reseña-header">
          <h5>${reseña.assistantName}</h5>
          <p><strong>Rating:</strong> ${reseña.rating} ⭐</p>
        </div>
        <p class="reseña-comment">${reseña.comment}</p>
        <hr />
      `;

      // Agregar la reseña al contenedor
      reseñasContainer.appendChild(reseñaDiv);
    });
  } catch (err) {
    console.error("Error cargando reseñas:", err);
    alert("❌ No se pudo cargar las reseñas.");
  }
}

// Función para consultar si el asistente existe por su nombre
async function consultarAsistentePorNombre(nombre) {
  try {
    // Usar el endpoint de filtro
    const response = await fetch(`${ASISTENTE_API_BASE_URL}?name=${encodeURIComponent(nombre)}`);
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

    const assistants = await response.json();

    // Verificamos si encontramos algún asistente con ese nombre
    if (assistants.length === 0) {
      return null;  // Si no se encuentra el asistente, retornamos null
    }

    // Suponemos que hay un solo asistente con ese nombre o seleccionamos el primero si hay varios
    return assistants[0];
  } catch (error) {
    console.error('Error al consultar el asistente:', error);
    return null;
  }
}
