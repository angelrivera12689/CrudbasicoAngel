import { urlBase } from '/FrontEnd/Js/constante.js';

const TICKET_API_BASE_URL = `${urlBase}tickets/`;

let allTickets = []; // Variable para almacenar todos los tickets cargados

document.addEventListener("DOMContentLoaded", function () {
  const tableBody = document.getElementById("tableBody");
  const updateModal = document.getElementById("ticket-update-modal");
  const modalOverlay = document.getElementById("modal-overlay");
  const updateForm = document.getElementById("ticket-update-form");

  // Elementos del filtro
  const filterBtn = document.getElementById("filter-btn");
  const filterEventName = document.getElementById("filter-event-name");
  const filterAssistantName = document.getElementById("filter-assistant-name");

  if (!tableBody || !updateModal || !modalOverlay || !updateForm) {
    console.error("❌ Elementos necesarios no encontrados en el DOM.");
    return;
  }

  // Cargar los tickets inicialmente sin filtros
  loadTickets();

  // Event listener para el filtro
  filterBtn.addEventListener("click", function () {
    const eventName = filterEventName.value.trim().toLowerCase();
    const assistantName = filterAssistantName.value.trim().toLowerCase();
    applyFilter(eventName, assistantName);  // Llamamos a la función de filtro
  });

  // Función para cargar los tickets desde el servidor
  async function loadTickets() {
    try {
      const response = await fetch(TICKET_API_BASE_URL);
      if (!response.ok) throw new Error("❌ Error al obtener los tickets");

      const data = await response.json();
      allTickets = data; // Guardamos todos los tickets en memoria
      renderTable(allTickets.slice(0, 15)); // Renderizamos solo los primeros 15 tickets
    } catch (error) {
      console.error(error);
      alert("🚨 No se pudieron cargar los tickets.");
    }
  }

  // Función para aplicar el filtro
  function applyFilter(eventName = '', assistantName = '') {
    const filteredTickets = allTickets.filter(ticket => {
      const ticketEventName = ticket.eventName?.toLowerCase() ?? '';
      const ticketAssistantName = ticket.assistantName?.toLowerCase() ?? '';

      // Verificamos si el nombre del evento o del asistente coincide con el filtro
      const eventNameMatch = ticketEventName.includes(eventName);
      const assistantNameMatch = ticketAssistantName.includes(assistantName);

      return eventNameMatch && assistantNameMatch;
    });

    renderTable(filteredTickets);  // Renderizamos la tabla con los tickets filtrados
  }

  // Función para renderizar la tabla de tickets
  function renderTable(data) {
    if (data.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="7">😕 No se encontraron tickets</td></tr>`;
      return;
    }

    tableBody.innerHTML = data.map(ticket => `
      <tr id="ticket-row-${ticket.idTicket}">
          <td>${ticket.eventName ?? '—'}</td>
          <td>${ticket.assistantName ?? '—'}</td>
          <td>$${Number(ticket.price).toFixed(2) ?? '—'}</td>
          <td>${ticket.seatNumber ?? '—'}</td>
          <td>${ticket.status ? 'Activo' : 'Inactivo'}</td>
          <td>${ticket.purchaseDate ? new Date(ticket.purchaseDate).toLocaleString() : '—'}</td>
          <td>
              <button class="btn btn-warning btn-sm" onclick="updateTicket(${ticket.idTicket})">Actualizar</button>
              <button class="btn btn-danger btn-sm" onclick="deleteTicket(${ticket.idTicket})">Eliminar</button>
          </td>
      </tr>
    `).join("");
  }

  async function getTicketById(ticketId) {
    try {
      const response = await fetch(`${TICKET_API_BASE_URL}${ticketId}`);
      if (!response.ok) throw new Error(`❌ Error ${response.status}: ${response.statusText}`);
      return await response.json();
    } catch (error) {
      console.error(error);
      alert("❌ No se pudo obtener el ticket.");
    }
  }

  async function updateTicket(ticketId) {
    const ticket = await getTicketById(ticketId);

    if (ticket) {
      document.getElementById("ticket-id-update").value = ticket.idTicket;
      document.getElementById("ticket-price-update").value = ticket.price;
      document.getElementById("ticket-seat-number-update").value = ticket.seatNumber;

      openUpdateModal();
    }
  }

  async function deleteTicket(ticketId) {
    const confirmDelete = confirm("¿Estás seguro de que deseas eliminar este ticket?");
    if (confirmDelete) {
      try {
        const response = await fetch(`${TICKET_API_BASE_URL}${ticketId}`, {
          method: "DELETE",
        });
        if (!response.ok) throw new Error(`Error ${response.status}: ${response.statusText}`);

        alert("✅ Ticket eliminado con éxito");
        document.getElementById(`ticket-row-${ticketId}`).remove();
      } catch (error) {
        console.error("Error al eliminar el ticket:", error);
        alert("❌ Error al eliminar el ticket.");
      }
    }
  }

  window.updateTicket = updateTicket;
  window.deleteTicket = deleteTicket;

  updateForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const id = document.getElementById("ticket-id-update").value.trim();
    const price = parseFloat(document.getElementById("ticket-price-update").value.trim());
    const seatNumber = document.getElementById("ticket-seat-number-update").value.trim();

    if (!id || isNaN(price) || !seatNumber) {
      alert("Todos los campos son obligatorios ⚠️");
      return;
    }

    try {
      const currentTicket = await getTicketById(id); // Conservar status

      const response = await fetch(`${TICKET_API_BASE_URL}${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          eventId: currentTicket.eventId,
          assistantId: currentTicket.assistantId,
          price,
          seatNumber,
          status: currentTicket.status
        })
      });

      if (!response.ok) throw new Error(`Error ${response.status}: ${response.statusText}`);

      alert("✅ Ticket actualizado con éxito");

      const updatedTicket = {
        ...currentTicket,
        price,
        seatNumber
      };

      updateTicketRow(updatedTicket);
      closeUpdateModal();
    } catch (error) {
      console.error("Error al actualizar el ticket:", error);
      alert("❌ Error al actualizar el ticket.");
    }
  });

  function updateTicketRow(ticket) {
    const row = document.getElementById(`ticket-row-${ticket.idTicket}`);
    if (row) {
      row.innerHTML = `
        <td>${ticket.eventName ?? '—'}</td>
        <td>${ticket.assistantName ?? '—'}</td>
        <td>$${Number(ticket.price).toFixed(2) ?? '—'}</td>
        <td>${ticket.seatNumber ?? '—'}</td>
        <td>${ticket.status ? 'Activo' : 'Inactivo'}</td>
        <td>${ticket.purchaseDate ? new Date(ticket.purchaseDate).toLocaleString() : '—'}</td>
        <td>
            <button class="btn btn-warning btn-sm" onclick="updateTicket(${ticket.idTicket})">Actualizar</button>
            <button class="btn btn-danger btn-sm" onclick="deleteTicket(${ticket.idTicket})">Eliminar</button>
        </td>
      `;
    }
  }

  function openUpdateModal() {
    updateModal.style.display = 'block';
    modalOverlay.style.display = 'block';
  }

  function closeUpdateModal() {
    updateModal.style.display = 'none';
    modalOverlay.style.display = 'none';
    updateForm.reset();
  }

  window.openUpdateModal = openUpdateModal;
  window.closeUpdateModal = closeUpdateModal;
});
