import { urlBase } from '/FrontEnd/js/constante.js'; // Asegúrate de que esta ruta sea correcta

// Constantes para las URLs de los filtros
const EVENTO_API_BASE_URL = `${urlBase}event-assistant/`; // API base para eventos y asistentes
const ASISTENTE_API_BASE_URL = `${urlBase}event-assistant/`; // La misma base de URL ya que ambas rutas comparten el mismo patrón

document.addEventListener('DOMContentLoaded', () => {
    const searchEventsBtn = document.getElementById('search-events-btn');
    const searchAssistantsBtn = document.getElementById('search-assistants-btn');
    const eventsList = document.getElementById('events-list');
    const assistantsList = document.getElementById('assistants-list');

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
                const errorData = await response.json();
                alert(errorData.message || 'No se encontraron eventos para el asistente');
                return;
            }

            const events = await response.json();
            eventsList.innerHTML = ''; // Limpiar la lista antes de mostrar nuevos resultados

            if (events.length > 0) {
                events.forEach(evento => {
                    const eventItem = document.createElement('div');
                    eventItem.classList.add('card');
                    eventItem.classList.add('evento-card');

                    // Verificación de campos antes de mostrarlos
                    const eventName = evento.event.eventName || 'Nombre no disponible';
                    const description = evento.event.description || 'Descripción no disponible';
                    const location = evento.event.location || 'Ubicación no disponible';
                    const category = evento.event.categoryEvent?.name || 'Categoría no disponible'; // Aquí accedemos a categoryEvent.name
                    const date = evento.event.date ? formatDateForDisplay(evento.event.date) : 'Fecha no disponible';
                    const imageUrl = evento.event.imageUrl || 'https://via.placeholder.com/300';

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
        const eventId = document.getElementById('event-id').value.trim();
        if (!eventId) {
            alert('Por favor, ingresa el ID del evento');
            return;
        }

        try {
            const response = await fetch(`${ASISTENTE_API_BASE_URL}event/${eventId}`);

            if (!response.ok) {
                const errorData = await response.json();
                alert(errorData.message || 'No se encontraron asistentes para el evento');
                return;
            }

            const assistants = await response.json();
            assistantsList.innerHTML = ''; // Limpiar la lista antes de mostrar nuevos resultados

            if (assistants.length > 0) {
                assistants.forEach(assistant => {
                    const assistantItem = document.createElement('div');
                    assistantItem.classList.add('card');
                    assistantItem.classList.add('assistant-card');
                    assistantItem.innerHTML = `
                        <div class="card-body">
                            <!-- Imagen del asistente -->
                            <img src="${assistant.assistant.imageUrl || 'https://via.placeholder.com/100'}" alt="Imagen del asistente" class="assistant-img">
                            <h5 class="card-title">${assistant.assistant.name || 'Nombre no disponible'}</h5>
                            <p class="card-text">${assistant.assistant.email || 'Email no disponible'}</p>
                            <p class="card-status">${assistant.assistant.status !== undefined ? (assistant.assistant.status ? 'Activo' : 'Inactivo') : 'Estado no disponible'}</p>
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

    // Función para formatear la fecha
    function formatDateForDisplay(dateString) {
        const date = new Date(dateString);
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return date.toLocaleDateString('es-ES', options);
    }
});
