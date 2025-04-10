// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/Js/constante.js';  // Asegúrate de que la ruta sea correcta

// Definir la URL base para las reseñas
const RESEÑA_API_BASE_URL = `${urlBase}reviews/`;  // Concatenamos la URL base con el endpoint de reseñas

console.log(RESEÑA_API_BASE_URL);  // Verifica que la URL esté correctamente formada

// ======================= REGISTRAR RESEÑA =======================
document.getElementById("review-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let comment = document.getElementById("review-comment").value.trim();
    let rating = parseInt(document.getElementById("review-rating").value.trim());
    let eventId = parseInt(document.getElementById("review-event-id").value.trim());
    let assistantId = parseInt(document.getElementById("review-assistant-id").value.trim());

    // Validar que los campos no estén vacíos y sean correctos
    if (!comment || isNaN(rating) || isNaN(eventId) || isNaN(assistantId)) {
        alert("Todos los campos son obligatorios y deben ser válidos.");
        return;
    }

    let bodyContent = JSON.stringify({
        comment: comment,
        rating: rating,
        eventId: eventId,
        assistantId: assistantId
    });

    let headersList = {
        "Accept": "*/*",
        "User-Agent": "Thunder Client (https://www.thunderclient.com)",
        "Content-Type": "application/json"
    };

    try {
        let response = await fetch(RESEÑA_API_BASE_URL, {  // Usamos la URL base de reseñas
            method: "POST",
            body: bodyContent,
            headers: headersList
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud: " + response.statusText);
        }

        let data = await response.json();
        console.log("Reseña registrada:", data);
        alert("Reseña registrada con éxito");
        document.getElementById("review-form").reset(); // Limpia el formulario
    } catch (error) {
        console.error("Error al registrar la reseña:", error);
        alert("Error al registrar la reseña.");
    }
});

// ======================= ACTUALIZAR RESEÑA =======================
document.getElementById("reseña-update-form").addEventListener("submit", async function(event) {
    event.preventDefault();

    let id = document.getElementById("reseña-id-update").value.trim();
    let comentario = document.getElementById("reseña-comentario-update").value.trim();
    let calificacion = document.getElementById("reseña-calificacion-update").value.trim();
    let eventoId = document.getElementById("reseña-evento-id-update").value.trim();
    let asistenteId = document.getElementById("reseña-asistente-id-update").value.trim();

    if (!id || !comentario || !calificacion || !eventoId || !asistenteId) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    if (calificacion < 1 || calificacion > 5) {
        alert("La calificación debe estar entre 1 y 5 ⭐");
        return;
    }

    let bodyContent = JSON.stringify({
        "comment": comentario,
        "rating": parseInt(calificacion),
        "eventId": parseInt(eventoId),
        "assistantId": parseInt(asistenteId)
    });

    try {
        let response = await fetch(`${RESEÑA_API_BASE_URL}${id}`, {  // Usamos la URL base de reseñas y el ID de la reseña a actualizar
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.json();

        if (response.ok) {
            alert("✅ Reseña actualizada con éxito 🎉");
            document.getElementById("reseña-update-form").reset();
        } else {
            alert(`❌ Error: ${data.message || "No se pudo actualizar la reseña"}`);
        }

        console.log("📢 Respuesta del servidor:", data);

    } catch (error) {
        console.error("❌ Error en la petición:", error);
        alert("❌ Error de conexión");
    }
});

// ======================= ELIMINAR RESEÑA =======================
document.getElementById("reseña-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita recarga

    const id = document.getElementById("reseña-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de tener este div en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${RESEÑA_API_BASE_URL}${id}`, {  // Usamos la URL base de reseñas y el ID de la reseña a eliminar
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Reseña con ID ${id} eliminada correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("reseña-delete-form").reset();
            alert("✅ Reseña eliminada correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar la reseña"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
