// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/js/constante.js';

// Definir las URL base para las APIs de evento, ticket y asistente
const EVENTO_API_BASE_URL = `${urlBase}events/`;
const TICKET_API_BASE_URL = `${urlBase}tickets/`;
const ASSISTANT_API_BASE_URL = `${urlBase}assistants?name=`; // URL para buscar asistente por nombre

document.addEventListener("DOMContentLoaded", () => {
  const params = new URLSearchParams(window.location.search);
  const eventoId = params.get("id");
  if (!eventoId) {
    return console.error("No se proporcionó ID de evento en la URL");
  }
  cargarDetallesEvento(eventoId);
});

// Función para formatear la fecha de forma amigable
function formatDateForDisplay(dateString) {
  const d = new Date(dateString);
  return [
    String(d.getDate()).padStart(2, "0"),
    String(d.getMonth() + 1).padStart(2, "0"),
    d.getFullYear()
  ].join("/");
}

// Cargar los detalles del evento
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
  } catch (err) {
    console.error("Error cargando detalles:", err);
    alert("❌ No se pudo cargar los detalles del evento.");
  }
}

