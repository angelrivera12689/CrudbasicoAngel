import { urlBase } from '/FrontEnd/Js/constante.js';  // Asegúrate que esta ruta sea válida

const RESEÑA_API_BASE_URL = `${urlBase}reviews/`;

console.log(RESEÑA_API_BASE_URL);

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
