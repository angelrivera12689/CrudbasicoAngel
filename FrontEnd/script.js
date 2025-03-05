// Función para abrir el modal y cargar la información del evento
function openModal(eventId) {
    // Mostrar el modal
    const modal = document.getElementById("eventModal");
    modal.style.display = "block";
  
    // Cambiar la información del modal según el evento
    if (eventId === 'evento1') {
      document.getElementById("modalTitle").innerText = "Evento 1: Gran Fiesta";
      document.getElementById("modalImage").src = "../img/evento1.jpg";
      document.getElementById("modalDescription").innerText = "Descripción completa del Evento 1. Detalles sobre la fiesta, el lugar, la fecha, y más.";
    } else if (eventId === 'evento2') {
      document.getElementById("modalTitle").innerText = "Evento 2: Concierto";
      document.getElementById("modalImage").src = "../img/evento2.jpg";
      document.getElementById("modalDescription").innerText = "Descripción completa del Evento 2. Detalles sobre el concierto, artistas, entradas, etc.";
    } else if (eventId === 'evento3') {
      document.getElementById("modalTitle").innerText = "Evento 3: Festival de Arte";
      document.getElementById("modalImage").src = "../img/evento3.jpg";
      document.getElementById("modalDescription").innerText = "Descripción completa del Evento 3. Información sobre las exposiciones, horarios, etc.";
    }
    // Agrega más eventos según lo necesites...
  }
  
  // Función para cerrar el modal
  function closeModal() {
    const modal = document.getElementById("eventModal");
    modal.style.display = "none";
  }

  
  