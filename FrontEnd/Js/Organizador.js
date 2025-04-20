// ======================= IMPORTS Y URL BASE =======================
import { urlBase } from '/FrontEnd/js/constante.js';
const API_URL_ORGANIZER = `${urlBase}organizer/`;

// ======================= CARGAR ORGANIZADORES =======================
async function fetchOrganizers() {
    try {
        let response = await fetch(API_URL_ORGANIZER);
        if (!response.ok) throw new Error("Error al obtener organizadores");

        let organizers = await response.json();
        const limitedOrganizers = organizers.slice(0, 6); // Limitar la cantidad de organizadores
        displayOrganizers(limitedOrganizers);
    } catch (error) {
        console.error("Error al cargar organizadores:", error);
    }
}

// ======================= MOSTRAR ORGANIZADORES =======================
function displayOrganizers(organizers) {
    const organizerList = document.getElementById("organizer-list");
    organizerList.innerHTML = ""; // Limpiar la lista antes de agregar nuevos organizadores

    organizers.forEach(organizer => {
        // Crear el contenedor principal del organizador
        const organizerBox = document.createElement("div");
        organizerBox.classList.add("box");

        // Crear el contenido interno del organizador (tarjeta)
        organizerBox.innerHTML = `
            <div class="organizer-card">
                <div class="organizer-header">
                    <img src="${organizer.imageUrl || 'images/default-avatar.png'}" 
                         alt="${organizer.name}" 
                         class="organizer-avatar">
                    <div class="organizer-actions-top">
                        <button class="edit-btn" 
                                title="Editar" 
                                data-id="${organizer.id_organizer}" 
                                data-name="${organizer.name}" 
                                data-phone="${organizer.phone}" 
                                data-email="${organizer.email}">✏️</button>
                        <button class="delete-btn" 
                                title="Eliminar" 
                                data-id="${organizer.id_organizer}">🗑️</button>
                    </div>
                </div>
                <div class="organizer-body">
                    <h3 class="organizer-name">${organizer.name}</h3>
                    <p class="organizer-info"><strong>📞 Teléfono:</strong> ${organizer.phone}</p>
                    <p class="organizer-info"><strong>✉️ Correo:</strong> ${organizer.email}</p>
                </div>
            </div>
        `;

        // Agregar el elemento del organizador a la lista
        organizerList.appendChild(organizerBox);
    });

    // Asignar los eventos de "Eliminar" después de que los elementos se agreguen al DOM
    document.querySelectorAll(".delete-btn").forEach(button => {
        button.addEventListener("click", async function () {
            const organizerId = this.dataset.id; // Capturar el ID del botón de eliminar
            console.log("ID del organizador a eliminar:", organizerId); // Verificar el ID capturado

            const organizerCard = this.closest('.organizer-card'); // Encontrar la tarjeta del organizador

            // Validar si el ID es válido
            if (!organizerId || organizerId === "undefined") {
                alert("❌ El ID del organizador no es válido.");
                return;
            }

            // Confirmar la eliminación con el usuario
            const confirmDelete = confirm(`¿Estás seguro de eliminar al organizador con ID ${organizerId}?`);

            if (confirmDelete) {
                try {
                    // Realizar la solicitud DELETE a la API
                    const response = await fetch(`${API_URL_ORGANIZER}${organizerId}`, {
                        method: "DELETE",
                        headers: { "Accept": "application/json" } // Especificar que esperamos JSON en la respuesta
                    });

                    // Procesar la respuesta de la API
                    if (response.ok) {
                        // Si la eliminación fue exitosa, remover la tarjeta del DOM
                        organizerCard.remove();
                        alert(`✅ Organizador con ID ${organizerId} eliminado correctamente.`);
                    } else {
                        // Si hubo un error en la eliminación, mostrar un mensaje
                        const errorData = await response.json();
                        alert(`❌ Error al eliminar: ${errorData.message || "No se pudo eliminar el organizador"}`);
                    }
                } catch (error) {
                    // Manejar errores de conexión u otros errores inesperados
                    console.error("❌ Error al eliminar organizador:", error);
                    alert("❌ Error de conexión al eliminar.");
                }
            }
        });
    });
}

// ======================= DOM Ready =======================
document.addEventListener("DOMContentLoaded", fetchOrganizers);

// ======================= REGISTRAR ORGANIZADOR =======================
document.getElementById("organizer-form").addEventListener("submit", async function(event) {
    event.preventDefault();

    let name = document.getElementById("organizador-nombre").value.trim();
    let phone = document.getElementById("organizador-telefono").value.trim();
    let email = document.getElementById("organizador-email").value.trim();
    let imageUrl = document.getElementById("organizador-imagen-url").value.trim();

    if (!name || !phone || !email) {
        alert("Todos los campos son obligatorios.");
        return;
    }

    let bodyContent = JSON.stringify({ name, phone, email, imageUrl });

    try {
        let response = await fetch(API_URL_ORGANIZER, {
            method: "POST",
            headers: { "Accept": "*/*", "Content-Type": "application/json" },
            body: bodyContent
        });

        if (!response.ok) throw new Error("Error en la solicitud: " + response.statusText);

        let data = await response.json();
        alert("Organizador registrado con éxito");
        document.getElementById("organizer-form").reset();
        fetchOrganizers(); // Actualiza la lista de organizadores
    } catch (error) {
        console.error("Error al registrar el organizador:", error);
        alert("Error al registrar el organizador.");
    }
});

// ======================= MANEJO DEL MODAL =======================
// Obtener el modal y los botones
const modal = document.getElementById("modal");
const openModalButton = document.getElementById("openModal");
const closeModalButton = document.querySelector(".close");

// Función para abrir el modal
openModalButton.addEventListener("click", () => {
    modal.style.display = "block"; // Muestra el modal
});

// Función para cerrar el modal
closeModalButton.addEventListener("click", () => {
    modal.style.display = "none"; // Oculta el modal
});

// Cerrar el modal si el usuario hace clic fuera del contenido del modal
window.addEventListener("click", (event) => {
    if (event.target === modal) {
        modal.style.display = "none"; // Oculta el modal si se hace clic fuera
    }
});
