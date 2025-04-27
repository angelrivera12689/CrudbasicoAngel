import { urlBase } from '/FrontEnd/js/constante.js'; // Asegúrate de que esta ruta sea correcta

// Constantes para las URLs de las APIs
const EVENTO_API_BASE_URL = `${urlBase}event-assistant/`;
const ASISTENTE_API_BASE_URL = `${urlBase}event-assistant/`;
const EVENTO_API = `${urlBase}events/`;
const ASISTENTE_API = `${urlBase}assistants/`;
const ASIGNAR_API = `${urlBase}event-assistant/`;

document.addEventListener('DOMContentLoaded', () => {
    const searchEventsBtn = document.getElementById('search-events-btn');
    const searchAssistantsBtn = document.getElementById('search-assistants-btn');
    const eventsList = document.getElementById('events-list');
    const assistantsList = document.getElementById('assistants-list');

    const eventSelect = document.getElementById('event-id');
    const assistantSelect = document.getElementById('assistant-id');
    const assignAssistantBtn = document.getElementById('assign-assistant-btn');

    // Cargar eventos y asistentes al inicio
    async function loadEventAndAssistantOptions() {
        try {
            // Cargar eventos
            const eventResponse = await fetch(`${EVENTO_API}`);
            const events = await eventResponse.json();
            eventSelect.innerHTML = ''; // Limpiar select de eventos para evitar duplicados

            if (events.length > 0) {
                events.forEach(evento => {
                    const option = document.createElement('option');
                    option.value = evento.idEvent;
                    option.textContent = evento.eventName;
                    eventSelect.appendChild(option);
                });
            }

            // Cargar asistentes
            const assistantResponse = await fetch(`${ASISTENTE_API}`);
            const assistants = await assistantResponse.json();
            assistantSelect.innerHTML = ''; // Limpiar select de asistentes para evitar duplicados

            if (assistants.length > 0) {
                assistants.forEach(assistant => {
                    const option = document.createElement('option');
                    option.value = assistant.id;
                    option.textContent = assistant.name;
                    assistantSelect.appendChild(option);
                });
            }
        } catch (error) {
            console.error('Error al cargar eventos y asistentes:', error);
            alert('Hubo un error al cargar los eventos y asistentes.');
        }
    }

    // Filtro para Buscar Eventos por Asistente
    searchEventsBtn.addEventListener('click', async () => {
        const assistantName = document.getElementById('assistant-name').value.trim();
        if (!assistantName) {
            alert('Por favor, ingresa el nombre del asistente');
            return;
        }

        try {
            const response = await fetch(`${EVENTO_API_BASE_URL}assistant/${assistantName}`);

            if (!response.ok) {
                const contentType = response.headers.get('content-type');
                let errorMessage = 'Error desconocido';

                if (contentType && contentType.includes('application/json')) {
                    const errorData = await response.json();
                    errorMessage = errorData.message || 'No se encontraron eventos para el asistente';
                } else {
                    errorMessage = await response.text();
                }

                alert(errorMessage);
                return;
            }

            const events = await response.json();
            eventsList.innerHTML = ''; // Limpiar lista
            eventSelect.innerHTML = ''; // Limpiar select de eventos

            if (events.length > 0) {
                events.forEach(evento => {
                    const eventItem = document.createElement('div');
                    eventItem.classList.add('card', 'evento-card');

                    const eventName = evento.event?.eventName || 'Nombre no disponible';
                    const description = evento.event?.description || 'Descripción no disponible';
                    const location = evento.event?.location || 'Ubicación no disponible';
                    const category = evento.event?.categoryEvent?.name || 'Categoría no disponible';
                    const date = evento.event?.date ? formatDateForDisplay(evento.event.date) : 'Fecha no disponible';
                    const imageUrl = evento.event?.imageUrl || 'https://via.placeholder.com/300';

                    eventItem.innerHTML = `
                        <img src="${imageUrl}" alt="Imagen del evento" class="card-img-top">
                        <div class="evento-info">
                            <h3>${eventName}</h3>
                            <p class="descripcion">${description}</p>
                            <p class="ubicacion">📍 ${location}</p>
                            <p class="categoria">🎫 Categoría: ${category}</p>
                            <p class="fecha">📅 Fecha: ${date}</p>
                            <div class="ver-detalles-container">
                                <a href="/FrontEnd/detalleEventos.html?id=${evento.event.idEvent}" class="btn-ver-detalles">Ver Detalles</a>
                            </div>
                        </div>
                    `;
                    eventsList.appendChild(eventItem);

                    // Agregar el evento al select (event-id)
                    const option = document.createElement('option');
                    option.value = evento.event.idEvent;
                    option.textContent = eventName;
                    eventSelect.appendChild(option);
                });
            } else {
                eventsList.innerHTML = '<p>No se encontraron eventos.</p>';
            }
        } catch (error) {
            console.error('Error al buscar eventos:', error);
            alert('Hubo un error al buscar los eventos.');
        }
    });

    // Filtro para Buscar Asistentes por Evento
    searchAssistantsBtn.addEventListener('click', async () => {
        const eventName = document.getElementById('event-name').value.trim(); // Se espera el nombre del evento
        if (!eventName) {
            alert('Por favor, ingresa el nombre del evento');
            return;
        }

        try {
            const response = await fetch(`${ASISTENTE_API_BASE_URL}event/${eventName}`);

            if (!response.ok) {
                const contentType = response.headers.get('content-type');
                let errorMessage = 'Error desconocido';

                if (contentType && contentType.includes('application/json')) {
                    const errorData = await response.json();
                    errorMessage = errorData.message || 'No se encontraron asistentes para el evento';
                } else {
                    errorMessage = await response.text();
                }

                alert(errorMessage);
                return;
            }

            const assistants = await response.json();
            assistantsList.innerHTML = ''; // Limpiar lista

            if (assistants.length > 0) {
                assistants.forEach(assistant => {
                    const assistantItem = document.createElement('div');
                    assistantItem.classList.add('card', 'assistant-card');
                    assistantItem.innerHTML = `
                        <div class="card-body">
                            <img src="${assistant.assistant?.imageUrl || 'https://via.placeholder.com/100'}" alt="Imagen del asistente" class="assistant-img">
                            <h5 class="card-title">${assistant.assistant?.name || 'Nombre no disponible'}</h5>
                            <p class="card-text">${assistant.assistant?.email || 'Email no disponible'}</p>
                            <p class="card-status">${assistant.assistant?.status !== undefined ? (assistant.assistant.status ? 'Activo' : 'Inactivo') : 'Estado no disponible'}</p>
                        </div>
                    `;
                    assistantsList.appendChild(assistantItem);
                });
            } else {
                assistantsList.innerHTML = '<p>No se encontraron asistentes.</p>';
            }
        } catch (error) {
            console.error('Error al buscar asistentes:', error);
            alert('Hubo un error al buscar los asistentes.');
        }
    });

    // Asignar asistente al evento
    assignAssistantBtn.addEventListener('click', async () => {
        const selectedEventId = eventSelect.value;
        const selectedAssistantId = assistantSelect.value;

        if (!selectedEventId || !selectedAssistantId) {
            alert('Por favor, selecciona un evento y un asistente.');
            return;
        }

        try {
            const response = await fetch(`${ASIGNAR_API}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    event: { idEvent: selectedEventId },
                    assistant: { id: selectedAssistantId },
                }),
            });

            if (response.ok) {
                alert('Asistente asignado correctamente al evento.');
            } else {
                const contentType = response.headers.get('content-type');
                let errorMessage = 'Error desconocido';

                if (contentType && contentType.includes('application/json')) {
                    const errorData = await response.json();
                    errorMessage = errorData.message || 'Error al asignar el asistente.';
                } else {
                    errorMessage = await response.text();
                }

                alert(errorMessage);
            }
        } catch (error) {
            console.error('Error al asignar asistente al evento:', error);
            alert('Hubo un error al asignar el asistente.');
        }
    });

    function formatDateForDisplay(dateString) {
        const date = new Date(dateString);
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return date.toLocaleDateString('es-ES', options);
    }

    loadEventAndAssistantOptions();
});
