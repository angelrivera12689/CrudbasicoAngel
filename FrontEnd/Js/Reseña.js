import { urlBase } from '/FrontEnd/Js/constante.js';  // Asegúrate que esta ruta sea válida

const RESEÑA_API_BASE_URL = `${urlBase}reviews/`;

console.log(RESEÑA_API_BASE_URL);

// ======================= REGISTRAR RESEÑA =======================
document.getElementById("reseña-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Obtener los valores del formulario
    let comment = document.getElementById("review-comment").value.trim();
    let rating = parseInt(document.getElementById("review-rating").value.trim());
    let eventId = parseInt(document.getElementById("review-event-id").value.trim());
    let assistantId = parseInt(document.getElementById("review-assistant-id").value.trim());

    // Validar los campos
    if (!comment || isNaN(rating) || isNaN(eventId) || isNaN(assistantId)) {
        alert("Todos los campos son obligatorios y deben ser válidos.");
        return;
    }

    // ✅ Ajuste aquí: Enviar solo el ID del evento directamente
    let bodyContent = JSON.stringify({
        comment: comment,
        rating: rating,
        eventId: eventId,  // Cambiado para enviar solo eventId y no un objeto
        assistantId: assistantId
    });

    // Cabeceras de la solicitud
    let headersList = {
        "Accept": "application/json",
        "Content-Type": "application/json"
    };

    try {
        // Realizar la solicitud POST para registrar la reseña
        let response = await fetch(RESEÑA_API_BASE_URL, {
            method: "POST",
            body: bodyContent,
            headers: headersList
        });

        // Comprobar si la respuesta es exitosa
        if (!response.ok) {
            // Obtener detalles del error del servidor
            const errorData = await response.json();
            throw new Error(`Error en la solicitud: ${response.statusText} - ${JSON.stringify(errorData)}`);
        }

        // Si la respuesta es exitosa, mostrar mensaje y limpiar formulario
        let data = await response.json();
        console.log("Reseña registrada:", data);
        alert("✅ Reseña registrada con éxito");
        document.getElementById("reseña-form").reset();  // Limpiar el formulario después de enviar
    } catch (error) {
        console.error("Error al registrar la reseña:", error);
        alert("❌ Error al registrar la reseña: " + error.message);  // Mostrar mensaje de error detallado
    }
});



// ======================= ACTUALIZAR RESEÑA =======================
document.getElementById("reseña-update").addEventListener("submit", async function(event) {
    event.preventDefault();

    let id = document.getElementById("reseña-id-update").value.trim();
    let comentario = document.getElementById("reseña-comentario-update").value.trim();
    let calificacion = parseInt(document.getElementById("reseña-calificacion-update").value.trim());
    let eventoId = parseInt(document.getElementById("reseña-evento-id-update").value.trim());
    let asistenteId = parseInt(document.getElementById("reseña-asistente-id-update").value.trim());

    if (!id || !comentario || isNaN(calificacion) || isNaN(eventoId) || isNaN(asistenteId)) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    if (calificacion < 1 || calificacion > 5) {
        alert("La calificación debe estar entre 1 y 5 ⭐");
        return;
    }

    let bodyContent = JSON.stringify({
        comment: comentario,
        rating: calificacion,
        event: { id: eventoId },
        assistant: { id: asistenteId }
    });

    try {
        let response = await fetch(`${RESEÑA_API_BASE_URL}${id}`, {
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
    event.preventDefault();

    const id = document.getElementById("reseña-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje");

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${RESEÑA_API_BASE_URL}${id}`, {
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
