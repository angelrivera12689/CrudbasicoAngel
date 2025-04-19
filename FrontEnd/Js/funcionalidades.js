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
  