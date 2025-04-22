import { urlBase } from '/FrontEnd/Js/constante.js';  // Asegúrate de que la ruta sea correcta

// Definir la URL base para los tickets y asistentes
const TICKET_API_BASE_URL = `${urlBase}tickets/`;
const ASSISTANT_API_BASE_URL = `${urlBase}assistants/filter`;

document.addEventListener("DOMContentLoaded", function () {
  // Capturar el ID del evento desde la URL
  const params = new URLSearchParams(window.location.search);
  const eventId = params.get("id");

  if (!eventId) {
    console.error("No se proporcionó ID de evento en la URL");
    alert("❌ No se encontró el ID del evento en la URL.");
    return;
  }

  // Elementos del modal
  const openModalBtn = document.getElementById("openCustomModalBtn");
  const customModal = document.getElementById("customTicketModal");
  const closeModalBtn = document.getElementById("closeCustomTicketBtn");

  // Ocultar el modal al cargar la página
  customModal.style.display = "none";

  // Abrir el modal al hacer clic en el botón
  openModalBtn.addEventListener("click", function (e) {
    e.preventDefault();
    customModal.style.display = "flex";
  });

  // Cerrar el modal al hacer clic en el botón de cerrar (X)
  closeModalBtn.addEventListener("click", function () {
    customModal.style.display = "none";
  });

  // Cerrar el modal al hacer clic fuera del contenido del modal
  window.addEventListener("click", function (e) {
    if (e.target === customModal) {
      customModal.style.display = "none";
    }
  });

  // Manejar el envío del formulario de tickets
  document.getElementById("ticket-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    const assistantName = document.getElementById("ticket-assistant-name").value.trim();
    const price = parseFloat(document.getElementById("ticket-price").value.trim());
    const seatNumber = document.getElementById("ticket-seat-number").value.trim();

    // Validar que los campos no estén vacíos y sean correctos
    if (!assistantName || isNaN(price) || !seatNumber) {
      alert("Todos los campos son obligatorios y deben ser válidos.");
      return;
    }

    if (price <= 0) {
      alert("El precio debe ser mayor a 0.");
      return;
    }

    try {
      // Verificar si el asistente existe
      const assistant = await consultarAsistentePorNombre(assistantName);

      if (!assistant) {
        alert("❌ No se encontró un asistente con ese nombre.");
        return;
      }

      // Crear el objeto del cuerpo de la solicitud
      const bodyContent = JSON.stringify({
        eventId: parseInt(eventId), // ID del evento capturado desde la URL
        assistantId: assistant.id, // ID del asistente obtenido de la API
        price: price,
        seatNumber: seatNumber
      });

      // Headers de la solicitud
      const headersList = {
        "Accept": "application/json",
        "Content-Type": "application/json"
      };

      // Realizar la solicitud POST para registrar el ticket
      const response = await fetch(TICKET_API_BASE_URL, {
        method: "POST",
        body: bodyContent,
        headers: headersList
      });

      // Verificar si la respuesta fue exitosa
      if (!response.ok) {
        const errorData = await response.json();
        console.error("Respuesta del servidor:", errorData);
        throw new Error("Error en la solicitud: " + (errorData.message || response.statusText));
      }

      const data = await response.json();
      console.log("Ticket registrado:", data);

      // Mostrar mensaje de éxito y limpiar el formulario
      alert("✅ Ticket registrado con éxito");
      document.getElementById("ticket-form").reset(); // Limpia el formulario
      customModal.style.display = "none"; // Cerrar el modal
    } catch (error) {
      // Manejar errores
      console.error("Error al registrar el ticket:", error);
      alert("❌ Error al registrar el ticket.");
    }
  });

  /**
   * Consulta el asistente por nombre.
   * @param {string} name - Nombre del asistente.
   * @returns {Object|null} - Datos del asistente o null si no se encuentra.
   */
  async function consultarAsistentePorNombre(name) {
    try {
      const response = await fetch(`${ASSISTANT_API_BASE_URL}?name=${encodeURIComponent(name)}`);
      if (!response.ok) throw new Error(`Error HTTP ${response.status}`);

      const assistants = await response.json();
      return assistants.length > 0 ? assistants[0] : null;
    } catch (error) {
      console.error("Error al consultar el asistente:", error);
      return null;
    }
  }
});