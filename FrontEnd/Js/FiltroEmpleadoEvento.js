import { urlBase } from '/FrontEnd/js/constante.js'; // Asegúrate de que esta ruta sea correcta

// Constantes para las URLs de las APIs
const FILTRAR_EMPLEADOS_API = `${urlBase}event-employees/by-event/`;
const FILTRAR_EVENTOS_API = `${urlBase}event-employees/by-employee/`;
const ASOCIAR_EMPLEADO_EVENTO_API = `${urlBase}event-employees/`;
const EVENTOS_API = `${urlBase}events/`;
const EMPLEADOS_API = `${urlBase}employee/`;

// Función para filtrar empleados por evento
async function filtrarEmpleadosPorEvento(eventName) {
  try {
    if (!eventName) {
      document.getElementById('employee-list-by-event').innerHTML = 'Por favor ingrese el nombre de un evento.';
      return;
    }

    const encodedEventName = encodeURIComponent(eventName.trim());
    const apiUrl = `${FILTRAR_EMPLEADOS_API}${encodedEventName}`;
    console.log(`URL generada para empleados por evento: ${apiUrl}`);

    const response = await fetch(apiUrl);
    if (!response.ok) throw new Error(`Error en la respuesta del servidor: ${response.status}`);

    const empleados = await response.json();
    const resultList = document.getElementById('employee-list-by-event');
    resultList.innerHTML = '';

    if (empleados.length === 0) {
      resultList.innerHTML = 'No se encontraron empleados para este evento.';
      return;
    }

    empleados.forEach(eventEmployee => {
      const empleado = eventEmployee.employee;
      const card = document.createElement('div');
      card.classList.add('col-md-4');
      card.innerHTML = `
        <div class="card">
          <img src="${empleado.imageUrl || 'https://via.placeholder.com/150'}" class="card-img-top" alt="Imagen del empleado">
          <div class="card-body">
            <h5 class="card-title">${empleado.first_name || 'Nombre no disponible'} ${empleado.last_name || 'Apellido no disponible'}</h5>
            <p class="card-text">${empleado.address || 'Dirección no disponible'}</p>
            <p class="card-text">Teléfono: ${empleado.phone_number || 'Número no disponible'}</p>
          </div>
        </div>
      `;
      resultList.appendChild(card);
    });
  } catch (error) {
    console.error('Error filtrando empleados por evento:', error);
    document.getElementById('employee-list-by-event').textContent = 'Ocurrió un error al cargar los empleados.';
  }
}

// Función para filtrar eventos por empleado
async function filtrarEventosPorEmpleado(employeeName) {
  try {
    if (!employeeName) {
      document.getElementById('event-list-by-employee').innerHTML = 'Por favor ingrese el nombre de un empleado.';
      return;
    }

    const encodedEmployeeName = encodeURIComponent(employeeName.trim());
    const apiUrl = `${FILTRAR_EVENTOS_API}${encodedEmployeeName}`;
    console.log(`URL generada para eventos por empleado: ${apiUrl}`);

    const response = await fetch(apiUrl);
    if (!response.ok) throw new Error(`Error en la respuesta del servidor: ${response.status}`);

    const eventos = await response.json();
    const resultList = document.getElementById('event-list-by-employee');
    resultList.innerHTML = '';

    if (eventos.length === 0) {
      resultList.innerHTML = 'No se encontraron eventos para este empleado.';
      return;
    }

    eventos.forEach(eventEmployee => {
      const evento = eventEmployee.event;
      const card = document.createElement('div');
      card.classList.add('col-md-4');
      card.innerHTML = `
        <div class="card">
          <img src="${evento.imageUrl || 'https://via.placeholder.com/150'}" class="card-img-top" alt="Imagen del evento">
          <div class="card-body">
            <h5 class="card-title">${evento.eventName || 'Evento no disponible'}</h5>
            <p class="card-text">${evento.description || 'Descripción no disponible'}</p>
            <p class="card-text">Fecha: ${evento.date || 'Fecha no disponible'}</p>
            <p class="card-text">Hora: ${evento.time || 'Hora no disponible'}</p>
          </div>
        </div>
      `;
      resultList.appendChild(card);
    });
  } catch (error) {
    console.error('Error filtrando eventos por empleado:', error);
    document.getElementById('event-list-by-employee').textContent = 'Ocurrió un error al cargar los eventos.';
  }
}

// Función para asociar un empleado con un evento (POST)
async function asociarEmpleadoAEvento(eventId, employeeId) {
  try {
    if (!eventId || !employeeId) {
      alert('Por favor seleccione tanto un evento como un empleado.');
      return;
    }

    const payload = {
      event: { idEvent: parseInt(eventId) },          // ✅ corregido aquí
      employee: { id_employee: parseInt(employeeId) } // ✅ corregido aquí
    };

    const response = await fetch(ASOCIAR_EMPLEADO_EVENTO_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    if (response.ok) {
      alert('El empleado fue asociado exitosamente al evento.');
    } else {
      const errorData = await response.json();
      alert(`Error al asociar empleado al evento: ${errorData.message || 'Error desconocido.'}`);
    }
  } catch (error) {
    console.error('Error al asociar empleado al evento:', error);
    alert('Ocurrió un error al asociar el empleado al evento.');
  }
}

// Función para cargar los eventos en el select
async function cargarEventos() {
  try {
    const response = await fetch(EVENTOS_API);
    if (!response.ok) throw new Error('Error al cargar eventos');

    const eventos = await response.json();
    const eventSelect = document.getElementById('associate-event-name');
    if (!eventSelect) return;

    eventSelect.innerHTML = '<option value="">Seleccione un evento</option>';

    eventos.forEach(evento => {
      const option = document.createElement('option');
      option.value = evento.idEvent; // ✅ cambiar id a idEvent
      option.textContent = evento.eventName;
      eventSelect.appendChild(option);
    });
  } catch (error) {
    console.error('Error cargando eventos:', error);
  }
}

// Función para cargar empleados en el select
async function cargarEmpleados() {
  try {
    const response = await fetch(EMPLEADOS_API);
    if (!response.ok) throw new Error('Error al cargar empleados');

    const empleados = await response.json();
    const employeeSelect = document.getElementById('associate-employee-name');
    if (!employeeSelect) return;

    employeeSelect.innerHTML = '<option value="">Seleccione un empleado</option>';

    empleados.forEach(empleado => {
      const option = document.createElement('option');
      option.value = empleado.id_employee; // ✅ cambiar id a id_employee
      option.textContent = `${empleado.first_name} ${empleado.last_name}`;
      employeeSelect.appendChild(option);
    });
  } catch (error) {
    console.error('Error cargando empleados:', error);
  }
}

// Inicialización al cargar la página
document.addEventListener('DOMContentLoaded', () => {
  // Configurar el formulario de filtrado de empleados por evento
  const filterEventForm = document.getElementById('filter-event-form');
  if (filterEventForm) {
    filterEventForm.addEventListener('submit', (event) => {
      event.preventDefault();
      const eventName = document.getElementById('filter-event-name').value;
      filtrarEmpleadosPorEvento(eventName);
    });
  }

  // Configurar el formulario de filtrado de eventos por empleado
  const filterEmployeeForm = document.getElementById('filter-employee-form');
  if (filterEmployeeForm) {
    filterEmployeeForm.addEventListener('submit', (event) => {
      event.preventDefault();
      const employeeName = document.getElementById('filter-employee-name').value;
      filtrarEventosPorEmpleado(employeeName);
    });
  }

  // Configurar el formulario de asociación de empleados a eventos
  const associateForm = document.getElementById('associate-form');
  if (associateForm) {
    associateForm.addEventListener('submit', (event) => {
      event.preventDefault();
      const eventId = document.getElementById('associate-event-name').value;
      const employeeId = document.getElementById('associate-employee-name').value;
      asociarEmpleadoAEvento(eventId, employeeId);
    });
  }

  // Cargar datos en los selects
  cargarEventos();
  cargarEmpleados();
});
