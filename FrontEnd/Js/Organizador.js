// ======================= IMPORTS Y URL BASE =======================
import { urlBase } from '/FrontEnd/js/constante.js';
const API_URL_ORGANIZER = `${urlBase}organizer/`;

// ======================= CARGAR ORGANIZADORES =======================
async function fetchOrganizers() {
  try {
    const resp = await fetch(API_URL_ORGANIZER);
    if (!resp.ok) throw new Error("Error al obtener organizadores");
    const organizers = await resp.json();
    displayOrganizers(organizers.slice(0, 6)); // Mostrar solo 6
  } catch (e) {
    console.error("Error al cargar organizadores:", e);
  }
}

// ======================= MOSTRAR ORGANIZADORES =======================
function displayOrganizers(organizers) {
  const list = document.getElementById("organizer-list");
  list.innerHTML = "";

  organizers.forEach(org => {
    const box = document.createElement("div");
    box.className = "box";
    const imageSrc = org.imageUrl
      ? `${org.imageUrl}?t=${Date.now()}`
      : 'images/default-avatar.png';
    box.innerHTML = `
      <div class="organizer-card" data-id="${org.id_organizer}">
        <div class="organizer-header">
          <img src="${imageSrc}" alt="${org.name}" class="organizer-avatar">
          <div class="organizer-actions-top">
            <button class="edit-btn"
                    title="Editar"
                    data-id="${org.id_organizer}"
                    data-name="${org.name}"
                    data-phone="${org.phone}"
                    data-email="${org.email}">✏️</button>
            <button class="delete-btn"
                    title="Eliminar"
                    data-id="${org.id_organizer}">🗑️</button>
          </div>
        </div>
        <div class="organizer-body">
          <h3 class="organizer-name">${org.name}</h3>
          <p class="organizer-info"><strong>📞 Teléfono:</strong> ${org.phone}</p>
          <p class="organizer-info"><strong>✉️ Correo:</strong> ${org.email}</p>
        </div>
      </div>
    `;
    list.appendChild(box);
  });
}

// ======================= ELIMINAR ORGANIZADOR =======================
document.addEventListener("click", async e => {
  if (!e.target.classList.contains("delete-btn")) return;

  const id = e.target.dataset.id;
  if (!confirm(`¿Eliminar organizador ${id}?`)) return;

  const resp = await fetch(API_URL_ORGANIZER + id, { method: "DELETE" });
  if (resp.ok) {
    alert("✅ Eliminado");
    fetchOrganizers();
  } else {
    const err = await resp.json();
    alert("❌ " + (err.message || "No se pudo eliminar"));
  }
});

// ======================= REGISTRAR ORGANIZADOR =======================
document.getElementById("organizer-form").addEventListener("submit", async e => {
  e.preventDefault();

  const name = e.target["organizador-nombre"].value.trim();
  const phone = e.target["organizador-telefono"].value.trim();
  const email = e.target["organizador-email"].value.trim();
  const imageUrl = e.target["organizador-imagen-url"].value.trim();

  if (!name || !phone || !email || !imageUrl) {
    return alert("Todos los campos son obligatorios");
  }

  const resp = await fetch(API_URL_ORGANIZER, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, phone, email, imageUrl })
  });

  if (resp.ok) {
    alert("✅ Registrado");
    e.target.reset();
    modalCreate.style.display = "none";
    fetchOrganizers();
  } else {
    alert("❌ Error al registrar");
  }
});

// ======================= MODAL CREAR =======================
const modalCreate = document.getElementById("modal");
document.getElementById("openModal").onclick = () => modalCreate.style.display = "block";
document.querySelector(".close").onclick = () => modalCreate.style.display = "none";
window.addEventListener("click", e => {
  if (e.target === modalCreate) modalCreate.style.display = "none";
});

// ======================= MODAL EDITAR =======================
const modalEdit = document.getElementById("modal-edit");
document.querySelector(".close-edit").onclick = () => modalEdit.style.display = "none";
window.addEventListener("click", e => {
  if (e.target === modalEdit) modalEdit.style.display = "none";
});

// ======================= ABRIR MODAL EDICIÓN =======================
document.addEventListener("click", e => {
  if (!e.target.classList.contains("edit-btn")) return;

  const btn = e.target;
  const id = btn.dataset.id;

  document.getElementById("edit-id-organizer").value = id;
  document.getElementById("edit-organizador-nombre").value = btn.dataset.name;
  document.getElementById("edit-organizador-telefono").value = btn.dataset.phone;
  document.getElementById("edit-organizador-email").value = btn.dataset.email;

  const fullImgUrl = btn.closest(".organizer-card").querySelector("img").src;
  const cleanImgUrl = fullImgUrl.split("?")[0];
  document.getElementById("edit-organizador-imagen-url").value = cleanImgUrl;

  // Mostrar imagen previa
  const imagePreview = document.getElementById("image-preview");
  if (cleanImgUrl) {
    imagePreview.src = cleanImgUrl;
    imagePreview.style.display = "block";
  } else {
    imagePreview.style.display = "none";
  }

  modalEdit.style.display = "block";
});

// ======================= ACTUALIZAR ORGANIZADOR =======================
document.getElementById("edit-organizer-form").addEventListener("submit", async e => {
  e.preventDefault();

  const id = document.getElementById("edit-id-organizer").value.trim();
  const name = document.getElementById("edit-organizador-nombre").value.trim();
  const phone = document.getElementById("edit-organizador-telefono").value.trim();
  const email = document.getElementById("edit-organizador-email").value.trim();
  const imageUrl = document.getElementById("edit-organizador-imagen-url").value.trim();

  if (!name || !phone || !email || !imageUrl) {
    return alert("Por favor, complete todos los campos.");
  }

  const response = await fetch(API_URL_ORGANIZER + id, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, phone, email, imageUrl })
  });

  if (response.ok) {
    alert("✅ Organizadores actualizados correctamente");
    modalEdit.style.display = "none";
    fetchOrganizers();
  } else {
    const error = await response.json();
    alert("❌ Error: " + (error.message || "Hubo un error al actualizar"));
  }
});
// ======================= ACTUALIZAR IMAGEN PREVIA EN EL MODAL =======================
document.getElementById("edit-organizador-imagen-url").addEventListener("input", function () {
    const imagePreview = document.getElementById("image-preview");
    const imageUrl = this.value.trim();

    if (imageUrl) {
        // Crear un objeto Image para comprobar si la URL es válida
        const img = new Image();
        img.onload = function () {
            imagePreview.src = `${imageUrl}?t=${Date.now()}`; // Forzar recarga de la imagen
            imagePreview.style.display = "block";
        };
        img.onerror = function () {
            imagePreview.style.display = "none";
            alert("La URL de la imagen no es válida.");
        };
        img.src = imageUrl;
    } else {
        imagePreview.style.display = "none"; // Ocultar imagen si la URL está vacía
    }
});

// ======================= INICIALIZAR =======================
document.addEventListener("DOMContentLoaded", fetchOrganizers);
