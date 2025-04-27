import { urlBase } from '/FrontEnd/js/constante.js'; // Asegúrate de que esta ruta sea correcta

// Constantes para las URLs de las APIs
const EVENTO_API = `${urlBase}events/`;
const ORGANIZADOR_API = `${urlBase}organizer/`;
const ASIGNAR_API = `${urlBase}event-organizer/`;

document.addEventListener("DOMContentLoaded", function () {
    const associateForm = document.getElementById("associate-form");
    const eventSelect = document.getElementById("event-id");
    const organizerSelect = document.getElementById("organizer-id");
    const associateMessage = document.getElementById("associate-message");

    // Verificar si los elementos necesarios existen en el DOM antes de continuar
    if (!associateForm || !eventSelect || !organizerSelect || !associateMessage) {
        console.error("Uno o más elementos no se encontraron en el DOM.");
        return;
    }

    // ------------ CARGAR EVENTOS Y ORGANIZADORES PARA EL FORMULARIO DE ASOCIAR ------------

    async function loadEventAndOrganizerOptions() {
        try {
            const eventResponse = await fetch(EVENTO_API);
            if (!eventResponse.ok) throw new Error("Error al cargar eventos");
            const eventos = await eventResponse.json();

            eventos.forEach(evento => {
                const option = document.createElement('option');
                option.value = evento.idEvent; // Asegúrate de que este campo sea correcto
                option.textContent = evento.eventName; // Nombre visible del evento
                eventSelect.appendChild(option);
            });

            const organizerResponse = await fetch(ORGANIZADOR_API);
            if (!organizerResponse.ok) throw new Error("Error al cargar organizadores");
            const organizadores = await organizerResponse.json();

            organizadores.forEach(organizador => {
                const option = document.createElement('option');
                option.value = organizador.id_organizer; // Asegúrate de que este campo sea correcto
                option.textContent = organizador.name; // Nombre visible del organizador
                organizerSelect.appendChild(option);
            });
        } catch (error) {
            console.error("Error cargando eventos y organizadores:", error);
        }
    }

    loadEventAndOrganizerOptions();

    // ------------ ASOCIAR ORGANIZADOR A EVENTO ------------

    associateForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const selectedEventId = eventSelect.value;   // Valor del evento seleccionado
        const selectedOrganizerId = organizerSelect.value;  // Valor del organizador seleccionado

        // Verificar que ambos campos estén seleccionados
        if (!selectedEventId || !selectedOrganizerId) {
            alert("Por favor, selecciona un evento y un organizador.");
            return;
        }

        try {
            const response = await fetch(ASIGNAR_API, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    event: { idEvent: parseInt(selectedEventId) }, // Asegurarse de enviar un número
                    organizer: { id_organizer: parseInt(selectedOrganizerId) } // Asegurarse de enviar un número
                }),
            });

            if (response.ok) {
                associateMessage.innerHTML = `<p style="color: green;">Asociación realizada con éxito.</p>`;
                associateForm.reset(); // Reseteamos el formulario
            } else {
                const contentType = response.headers.get('content-type');
                let errorMessage = 'Error desconocido';

                if (contentType && contentType.includes('application/json')) {
                    const errorData = await response.json();
                    errorMessage = errorData.message || 'Error al asociar el organizador.';
                } else {
                    errorMessage = await response.text();
                }

                associateMessage.innerHTML = `<p style="color: red;">${errorMessage}</p>`;
            }
        } catch (error) {
            console.error('Error al asociar organizador al evento:', error);
            associateMessage.innerHTML = `<p style="color: red;">Hubo un error al asociar el organizador.</p>`;
        }
    });

    // ------------ FILTRAR ORGANIZADORES POR EVENTO ------------

    const filterEventForm = document.getElementById("filter-event-form");
    const organizerListByEvent = document.getElementById("organizer-list-by-event");

    if (!filterEventForm || !organizerListByEvent) {
        console.error("Elementos no encontrados en el DOM para filtrar organizadores por evento.");
        return;
    }

    filterEventForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const eventName = document.getElementById("event-name").value.trim();

        if (!eventName) {
            alert("Por favor, ingresa el nombre del evento.");
            return;
        }

        const encodedEventName = encodeURIComponent(eventName);
        const apiUrl = `${urlBase}event-organizer/by-event/${encodedEventName}`;

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener organizadores: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                organizerListByEvent.innerHTML = "";

                if (data.length > 0) {
                    data.forEach(item => {
                        const organizer = item.organizer || {};
                        const organizerName = organizer.name || 'No disponible';
                        const organizerPhone = organizer.phone || 'No disponible';
                        const organizerEmail = organizer.email || 'No disponible';
                        const organizerStatus = organizer.status ? "Activo" : "Inactivo";
                        const organizerImage = organizer.imageUrl || '/default-image.jpg';

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

                        organizerListByEvent.appendChild(organizerItem);
                    });
                } else {
                    organizerListByEvent.innerHTML = "<p>No se encontraron organizadores para este evento.</p>";
                }
            })
            .catch(error => {
                console.error("Error al obtener organizadores:", error);
                organizerListByEvent.innerHTML = `<p>Error al cargar los organizadores. Detalles: ${error.message}</p>`;
            });
    });

    // ------------ FILTRAR EVENTOS POR ORGANIZADOR ------------

    const filterOrganizerForm = document.getElementById("filter-organizer-form");
    const eventListByOrganizer = document.getElementById("event-list-by-organizer");

    if (!filterOrganizerForm || !eventListByOrganizer) {
        console.error("Elementos no encontrados en el DOM para filtrar eventos por organizador.");
        return;
    }

    filterOrganizerForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const organizerName = document.getElementById("organizer-name").value.trim();

        if (!organizerName) {
            alert("Por favor, ingresa el nombre del organizador.");
            return;
        }

        const encodedOrganizerName = encodeURIComponent(organizerName);
        const apiUrl = `${urlBase}event-organizer/by-organizer/${encodedOrganizerName}`;

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener eventos: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                eventListByOrganizer.innerHTML = "";

                if (data.length > 0) {
                    data.forEach(item => {
                        const event = item.event || {};
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

                        eventListByOrganizer.appendChild(eventItem);
                    });
                } else {
                    eventListByOrganizer.innerHTML = "<p>No se encontraron eventos para este organizador.</p>";
                }
            })
            .catch(error => {
                console.error("Error al obtener eventos:", error);
                eventListByOrganizer.innerHTML = `<p>Error al cargar los eventos. Detalles: ${error.message}</p>`;
            });
    });
});
