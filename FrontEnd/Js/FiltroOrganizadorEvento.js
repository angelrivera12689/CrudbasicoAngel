// Asegúrate de que esta ruta sea correcta
// Asegúrate de que esta ruta sea correcta
import { urlBase } from '/FrontEnd/js/constante.js';

document.addEventListener("DOMContentLoaded", function () {
    const filterEventForm = document.getElementById("filter-event-form");
    const filterOrganizerForm = document.getElementById("filter-organizer-form");
    const organizerListByEvent = document.getElementById("organizer-list-by-event");
    const eventListByOrganizer = document.getElementById("event-list-by-organizer");

    if (!filterEventForm || !filterOrganizerForm || !organizerListByEvent || !eventListByOrganizer) {
        console.error("Uno o más elementos no se encontraron en el DOM.");
        return;
    }

    // Filtrar organizadores por evento
    filterEventForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const eventName = document.getElementById("event-name").value.trim();

        if (!eventName) {
            alert("Por favor, ingresa el nombre del evento.");
            return;
        }

        // Codificar el nombre del evento para la URL
        const encodedEventName = encodeURIComponent(eventName);

        // Usar la constante BASE_URL para construir la URL de la API
        const apiUrl = `${urlBase}event-organizer/by-event/${encodedEventName}`;

        // Hacer la solicitud al backend
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener organizadores: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                // Limpiar la lista actual de organizadores
                organizerListByEvent.innerHTML = "";

                if (data.length > 0) {
                    // Mostrar los organizadores filtrados
                    data.forEach(item => {
                        const organizer = item.organizer || {}; // Asegurarse de que 'organizer' existe
                        const event = item.event || {}; // Asegurarse de que 'event' existe

                        // Acceder a los campos de los organizadores
                        const organizerName = organizer.name || 'No disponible';
                        const organizerPhone = organizer.phone || 'No disponible';
                        const organizerEmail = organizer.email || 'No disponible';
                        const organizerStatus = organizer.status ? "Activo" : "Inactivo";
                        const organizerImage = organizer.imageUrl || '/default-image.jpg';

                        // Acceder a los campos de los eventos
                        const eventName = event.eventName || 'No disponible';
                        const eventLocation = event.location || 'No disponible';
                        const eventDate = event.date || 'No disponible';
                        const eventImage = event.imageUrl || '/default-image.jpg';

                        const organizerItem = document.createElement("div");
                        organizerItem.classList.add("organizer-item");

                        organizerItem.innerHTML = `
                            <div class="organizer-info">
                                <img src="${organizerImage}" alt="${organizerName}" class="organizer-image">
                                <h3>${organizerName}</h3>
                                <p><strong>Teléfono:</strong> ${organizerPhone}</p>
                                <p><strong>Correo:</strong> ${organizerEmail}</p>
                                <p><strong>Estado:</strong> ${organizerStatus}</p>
                            </div>
                          
                        `;

                        // Agregar el organizador al contenedor
                        organizerListByEvent.appendChild(organizerItem);
                    });
                } else {
                    // Si no hay resultados, mostrar mensaje
                    organizerListByEvent.innerHTML = "<p>No se encontraron organizadores para este evento.</p>";
                }
            })
            .catch(error => {
                console.error("Error al obtener organizadores:", error);
                organizerListByEvent.innerHTML = `<p>Error al cargar los organizadores. Detalles: ${error.message}</p>`;
            });
    });

    // Filtrar eventos por organizador
    filterOrganizerForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const organizerName = document.getElementById("organizer-name").value.trim();

        if (!organizerName) {
            alert("Por favor, ingresa el nombre del organizador.");
            return;
        }

        // Codificar el nombre del organizador para la URL
        const encodedOrganizerName = encodeURIComponent(organizerName);

        // Usar la constante BASE_URL para construir la URL de la API
        const apiUrl = `${urlBase}event-organizer/by-organizer/${encodedOrganizerName}`;

        // Hacer la solicitud al backend
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener eventos: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                // Limpiar la lista actual de eventos
                eventListByOrganizer.innerHTML = "";

                if (data.length > 0) {
                    // Mostrar los eventos filtrados
                    data.forEach(item => {
                        const event = item.event || {}; // Asegurarse de que 'event' existe

                        const eventName = event.eventName || 'No disponible';
                        const eventDate = event.date || 'No disponible';
                        const eventLocation = event.location || 'No disponible';
                        const eventStatus = event.status ? "Activo" : "Inactivo";
                        const eventImage = event.imageUrl || '/default-image.jpg';

                        const eventItem = document.createElement("div");
                        eventItem.classList.add("event-item");

                        eventItem.innerHTML = `
                            <div class="event-info">
                                <h3>${eventName}</h3>
                                <p><strong>Fecha:</strong> ${eventDate}</p>
                                <p><strong>Ubicación:</strong> ${eventLocation}</p>
                                <p><strong>Estado:</strong> ${eventStatus}</p>
                                <img src="${eventImage}" alt="${eventName}" class="event-image">
                            </div>
                        `;

                        // Agregar el evento al contenedor
                        eventListByOrganizer.appendChild(eventItem);
                    });
                } else {
                    // Si no hay resultados, mostrar mensaje
                    eventListByOrganizer.innerHTML = "<p>No se encontraron eventos para este organizador.</p>";
                }
            })
            .catch(error => {
                console.error("Error al obtener eventos:", error);
                eventListByOrganizer.innerHTML = `<p>Error al cargar los eventos. Detalles: ${error.message}</p>`;
            });
    });
});
