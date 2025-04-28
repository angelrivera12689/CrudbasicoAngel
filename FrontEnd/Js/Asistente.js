// Asistente.js
import { urlBase } from '/FrontEnd/Js/constante.js';
const API = `${urlBase}assistants/`;

// --- Elementos del DOM ---
const assistantList     = document.getElementById('assistant-list');
const assistantForm     = document.getElementById('assistant-form');
const modalOverlay      = document.getElementById('updateAssistantModal');
const closeModalBtn     = document.getElementById('closeUpdateModalBtn');
const updateForm        = document.getElementById('update-assistant-form');
const filterForm        = document.getElementById('filter-assistant-form'); // Nuevo: Formulario de filtro

// --- Inicialización ---
document.addEventListener('DOMContentLoaded', loadAssistants);
assistantForm.addEventListener('submit', registerAssistant);
filterForm.addEventListener('submit', filterAssistants); // Nuevo: Escuchar envío del filtro

// Cerrar modal al pulsar la X o al hacer clic fuera de la caja
closeModalBtn.addEventListener('click', () => modalOverlay.style.display = 'none');
modalOverlay.addEventListener('click', e => {
  if (e.target === modalOverlay) modalOverlay.style.display = 'none';
});

// --- 1) Registrar nuevo asistente ---
async function registerAssistant(e) {
  e.preventDefault();
  const name     = document.getElementById('asistente-nombre').value.trim();
  const email    = document.getElementById('asistente-email').value.trim();
  const phone    = document.getElementById('asistente-phone').value.trim();
  const imageUrl = document.getElementById('asistente-image-url').value.trim();

  try {
    const res = await fetch(API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, phone, imageUrl })
    });
    if (!res.ok) {
      const err = await res.json();
      throw new Error(err.message || res.statusText);
    }
    alert('¡Registro exitoso!');
    e.target.reset();
    loadAssistants();
  } catch (err) {
    alert('Error al registrar: ' + err.message);
  }
}

// --- 2) Cargar y renderizar lista completa ---
async function loadAssistants() {
  try {
    const res = await fetch(API);
    if (!res.ok) throw '';
    let list = await res.json();

    // Mostrar solo los primeros 8 asistentes
    list = list.slice(0, 8);

    renderAssistants(list);
  } catch {
    alert('No se pudo cargar asistentes');
  }
}

// --- 2.1) Función para renderizar asistentes (común para lista y filtro) ---
function renderAssistants(list) {
  assistantList.innerHTML = '';

  if (!list.length) {
    assistantList.innerHTML = `<li class="list-group-item text-center">No hay asistentes.</li>`;
    return;
  }

  list.forEach(a => {
    assistantList.insertAdjacentHTML('afterbegin', `
      <li class="list-group-item position-relative">
        <img src="${a.imageUrl}" 
             style="width:60px;height:60px;border-radius:50%;border:2px solid #007bff;display:block;margin:0 auto 10px;">
        <strong>${a.name}</strong><br>
        ${a.email}<br>
        ${a.phone}
        <div class="assistant-actions">
          <button class="custom-edit-btn" onclick="openUpdateModal('${a.id}')">✏️</button>
          <button class="custom-delete-btn" onclick="deleteAssistant('${a.id}')">🗑️</button>
        </div>
      </li>
    `);
  });
}

// --- 3) Eliminar asistente ---
window.deleteAssistant = async function(id) {
  if (!confirm('¿Eliminar este asistente?')) return;
  try {
    const res = await fetch(API + id, { method: 'DELETE' });
    if (!res.ok) throw '';
    loadAssistants();
  } catch {
    alert('Error al eliminar');
  }
};

// --- 4) Abrir modal y precargar datos ---
window.openUpdateModal = function(id) {
  fetch(API + id)
    .then(r => r.json())
    .then(a => {
      // Llenar formulario del modal
      document.getElementById('update-asistente-nombre').value = a.name;
      document.getElementById('update-asistente-email').value = a.email;
      document.getElementById('update-asistente-phone').value = a.phone;
      document.getElementById('update-asistente-image-url').value = a.imageUrl;
      updateForm.dataset.id = id;
      modalOverlay.style.display = 'flex';
    })
    .catch(() => alert('Asistente no encontrado'));
};

// --- 5) Actualizar asistente ---
updateForm.addEventListener('submit', async function(e) {
  e.preventDefault();
  const id       = this.dataset.id;
  const name     = document.getElementById('update-asistente-nombre').value.trim();
  const email    = document.getElementById('update-asistente-email').value.trim();
  const phone    = document.getElementById('update-asistente-phone').value.trim();
  const imageUrl = document.getElementById('update-asistente-image-url').value.trim();

  try {
    const res = await fetch(API + id, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, phone, imageUrl })
    });

    if (res.ok) {
      alert('Asistente actualizado correctamente');
      modalOverlay.style.display = 'none';
      loadAssistants();
    } else {
      const err = await res.json();
      alert(`Error al actualizar: ${err.message || res.statusText}`);
    }
  } catch (error) {
    console.error('Error de conexión al actualizar:', error);
    alert('Error de conexión al actualizar.');
  }
});

// --- 6) Filtrar asistentes ---
async function filterAssistants(e) {
  e.preventDefault();

  // Obtener valores del formulario de filtro
  const name   = document.getElementById('filter-name').value.trim();
  const phone  = document.getElementById('filter-phone').value.trim();
  const email  = document.getElementById('filter-email').value.trim();
  const status = document.getElementById('filter-status').value.trim();

  // Verificar si no hay filtros seleccionados, y recargar los primeros 8 asistentes
  if (!name && !phone && !email && !status) {
    loadAssistants(); // Vuelve a cargar los primeros 8 asistentes si no hay filtros
    return;
  }

  // Construir query solo con los filtros que se llenen
  let query = [];
  if (name)   query.push(`name=${encodeURIComponent(name)}`); // nombre esperado en el backend
  if (phone)  query.push(`phone=${encodeURIComponent(phone)}`);
  if (email)  query.push(`email=${encodeURIComponent(email)}`);
  if (status) query.push(`status=${encodeURIComponent(status)}`);

  let queryString = query.length ? `?${query.join('&')}` : '';

  try {
    const res = await fetch(`${urlBase}assistants/filter${queryString}`);
    if (!res.ok) throw '';

    const filteredList = await res.json();
    renderAssistants(filteredList);
  } catch {
    alert('No se pudo filtrar asistentes');
  }
}
