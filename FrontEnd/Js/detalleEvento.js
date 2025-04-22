// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/js/constante.js';

// Definir la URL base para eventos, asistentes y reseñas
const EVENTO_API_BASE_URL = `${urlBase}events/`;
const ASISTENTE_API_BASE_URL = `${urlBase}assistants/filter`;
const RESEÑA_API_BASE_URL = `${urlBase}reviews/`;

document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const eventoId = params.get("id");

  if (!eventoId) {
    console.error("No se proporcionó ID de evento en la URL");
    alert("❌ No se encontró el ID del evento en la URL.");
    return;
  }

  cargarDetallesEvento(eventoId);
  cargarReseñasEvento(eventoId);

  const filterForm = document.getElementById("filter-reviews-form");
  const clearFiltersButton = document.getElementById("clear-filters");

  if (filterForm) {
    filterForm.addEventListener("submit", e => {
      e.preventDefault();
      aplicarFiltros(eventoId);
    });
  }

  if (clearFiltersButton) {
    clearFiltersButton.addEventListener("click", () => {
      document.getElementById("filter-author").value = "";
      document.getElementById("filter-rating").value = "";
      cargarReseñasEvento(eventoId); // Recargar sin filtros
    });
  }

  const openResenaBtn = document.querySelector('.btn-outline-primary');
  const resenaModal = document.getElementById('resenaModal');
  const closeResenaBtn = document.getElementById('closeResenaBtn');
  const resenaForm = document.getElementById('review-form');
  const assistantNameInput = document.getElementById('review-assistant-name');

  if (openResenaBtn) {
    openResenaBtn.addEventListener('click', () => {
      resenaModal.style.display = 'flex';
      window.scrollTo(0, document.body.scrollHeight);
    });
  }

  if (closeResenaBtn) {
    closeResenaBtn.addEventListener('click', () => {
      resenaModal.style.display = 'none';
    });
  }

  if (resenaModal) {
    resenaModal.addEventListener('click', e => {
      if (e.target === resenaModal) {
        resenaModal.style.display = 'none';
      }
    });
  }

  if (resenaForm) {
    resenaForm.addEventListener('submit', async e => {
      e.preventDefault();

      const assistantName = assistantNameInput.value.trim();

      if (!assistantName) {
        alert("El nombre del asistente es obligatorio.");
        return;
      }

      try {
        const assistant = await consultarAsistentePorNombre(assistantName);

        if (!assistant) {
          alert("No se encontró un asistente con ese nombre.");
          return;
        }

        const reviewData = {
          comment: document.getElementById('review-comment').value.trim(),
          rating: parseInt(document.getElementById('review-rating').value),
          eventId: parseInt(eventoId),
          assistantId: assistant.id
        };

        if (!reviewData.comment || isNaN(reviewData.rating) || isNaN(reviewData.eventId) || isNaN(reviewData.assistantId)) {
          alert("Todos los campos son obligatorios y deben ser válidos.");
          return;
        }

        const response = await fetch(RESEÑA_API_BASE_URL, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(reviewData)
        });

        if (!response.ok) {
          const errorResponse = await response.json();
          console.error("Respuesta del servidor:", errorResponse);
          throw new Error(`HTTP error! status: ${response.status} - ${errorResponse.message || 'Verifica el servidor para más detalles'}`);
        }

        alert('¡Reseña enviada con éxito!');
        resenaModal.style.display = 'none';
        resenaForm.reset();
        cargarReseñasEvento(eventoId);
      } catch (error) {
        console.error('Error al enviar la reseña:', error);
        alert('❌ Error al enviar la reseña. Intenta de nuevo.');
      }
    });
  }
});

async function cargarDetallesEvento(eventoId) {
  try {
    const resp = await fetch(`${EVENTO_API_BASE_URL}${eventoId}`);
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const evento = await resp.json();

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

async function cargarReseñasEvento(eventoId) {
  try {
    const resp = await fetch(`${RESEÑA_API_BASE_URL}event/${eventoId}`);
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const reseñas = await resp.json();

    const reseñasContainer = document.getElementById("reseñas-container");
    reseñasContainer.innerHTML = "";

    if (reseñas.length === 0) {
      reseñasContainer.innerHTML = "<p>No hay reseñas para este evento aún.</p>";
      return;
    }

    reseñas.forEach(reseña => {
      const reseñaDiv = document.createElement("div");
      reseñaDiv.classList.add("reseña", "reseña-style");
      reseñaDiv.innerHTML = `
        <div class="reseña-header">
          <h5>${reseña.assistant?.name || "Asistente desconocido"}</h5>
          <p><strong>Rating:</strong> ${reseña.rating} ⭐</p>
        </div>
        <p class="reseña-comment">${reseña.comment}</p>
        <hr />
      `;
      reseñasContainer.appendChild(reseñaDiv);
    });
  } catch (err) {
    console.error("Error cargando reseñas:", err);
    alert("❌ No se pudo cargar las reseñas. Por favor, intenta nuevamente.");
  }
}

async function consultarAsistentePorNombre(nombre) {
  try {
    const response = await fetch(`${ASISTENTE_API_BASE_URL}?name=${encodeURIComponent(nombre)}`);
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
    const assistants = await response.json();
    return assistants.length > 0 ? assistants[0] : null;
  } catch (error) {
    console.error('Error al consultar el asistente:', error);
    return null;
  }
}

async function aplicarFiltros(eventoId) {
  const author = document.getElementById("filter-author").value.trim();
  const rating = parseInt(document.getElementById("filter-rating").value);

  try {
    let url = `${RESEÑA_API_BASE_URL}filter?eventId=${eventoId}`;
    if (author) url += `&author=${encodeURIComponent(author)}`;
    if (!isNaN(rating)) url += `&rating=${rating}`;

    const resp = await fetch(url);
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
    const reseñas = await resp.json();
    const reseñasContainer = document.getElementById("reseñas-container");
    reseñasContainer.innerHTML = "";

    if (reseñas.length === 0) {
      reseñasContainer.innerHTML = "<p>No hay reseñas que coincidan con los filtros.</p>";
      return;
    }

    reseñas.forEach(reseña => {
      const reseñaDiv = document.createElement("div");
      reseñaDiv.classList.add("reseña", "reseña-style");
      reseñaDiv.innerHTML = `
        <div class="reseña-header">
          <h5>${reseña.assistant?.name || "Asistente desconocido"}</h5>
          <p><strong>Rating:</strong> ${reseña.rating} ⭐</p>
        </div>
        <p class="reseña-comment">${reseña.comment}</p>
        <hr />
      `;
      reseñasContainer.appendChild(reseñaDiv);
    });
  } catch (err) {
    console.error("Error aplicando filtros:", err);
    alert("❌ No se pudieron aplicar los filtros.");
  }
}

function formatDateForDisplay(dateString) {
  const d = new Date(dateString);
  return `${String(d.getDate()).padStart(2, "0")}/${String(d.getMonth() + 1).padStart(2, "0")}/${d.getFullYear()}`;
}