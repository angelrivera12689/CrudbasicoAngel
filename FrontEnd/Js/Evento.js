// Definir la URL base para la API de eventos
// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/Js/constante.js';  // Asegúrate de que la ruta sea correcta

// Definir la URL base para la API de eventos
const EVENTO_API_BASE_URL = `${urlBase}event/`;  // Concatenando la URL base con la ruta de eventos

console.log( EVENTO_API_BASE_URL);  // Verifica que la URL esté correctamente formada


document.getElementById("evento-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    // Deshabilitar el botón de envío para evitar múltiples envíos
    const submitButton = document.getElementById("submit-btn");
    submitButton.disabled = true;

    let eventName = document.getElementById("evento-nombre").value.trim();
    let description = document.getElementById("evento-descripcion").value.trim();
    let location = document.getElementById("evento-ubicacion").value.trim();
    let categoryId = parseInt(document.getElementById("evento-categoria").value.trim());
    let imageUrl = document.getElementById("evento-imagen").value.trim();

    if (!eventName || !description || !location || !imageUrl || isNaN(categoryId)) {
        alert("Todos los campos son obligatorios, y la categoría debe ser un número válido.");
        submitButton.disabled = false;  // Volver a habilitar el botón en caso de error
        return;
    }

    // 👇 No se envían ni date ni time
    let bodyContent = JSON.stringify({
        eventName: eventName,
        description: description,
        location: location,
        categoryId: categoryId,
        imageUrl: imageUrl
    });

    try {
        let response = await fetch(`${EVENTO_API_BASE_URL}events/`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "*/*"
            },
            body: bodyContent
        });

        if (!response.ok) {
            const errorBody = await response.text();
            throw new Error("Error en la solicitud: " + response.status + " - " + errorBody);
        }

        let data = await response.json();
        console.log("✅ Evento registrado:", data);
        alert("✅ Evento registrado con éxito");
        document.getElementById("evento-form").reset();
    } catch (error) {
        console.error("❌ Error al registrar el evento:", error);
        alert("❌ Error al registrar el evento. Revisa consola.");
    } finally {
        // Volver a habilitar el botón de envío después de completar la solicitud
        submitButton.disabled = false;
    }
});



// ======================= ACTUALIZAR EVENTO =======================
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("evento-update-form").addEventListener("submit", async function (event) {
        event.preventDefault();

        let id = document.getElementById("evento-id").value.trim();
        let nombre = document.getElementById("evento-nombre-update").value.trim();
        let descripcion = document.getElementById("evento-descripcion-update").value.trim();
        let ubicacion = document.getElementById("evento-ubicacion-update").value.trim();
        let categoria = document.getElementById("evento-categoria-update").value.trim();
        let imageUrl = document.getElementById("evento-imagen-update").value.trim();

        if (!id || !nombre || !descripcion || !ubicacion || !categoria || !imageUrl) {
            alert("⚠️ Todos los campos son obligatorios.");
            return;
        }

        let bodyContent = JSON.stringify({
            "eventName": nombre,
            "description": descripcion,
            "location": ubicacion,
            "categoryId": parseInt(categoria),
            "imageUrl": imageUrl
        });

        let url = `${EVENTO_API_BASE_URL}events/${id}`;  // Usamos EVENTO_API_BASE_URL
        console.log(`📡 Enviando petición a: ${url}`);
        console.log("📦 Enviando datos:", bodyContent);

        try {
            let response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                body: bodyContent
            });

            let data;
            try {
                data = await response.json();
            } catch (jsonError) {
                console.error("⚠️ Error convirtiendo la respuesta a JSON:", jsonError);
                data = null;
            }

            if (response.ok) {
                alert("✅ Evento actualizado con éxito.");
                document.getElementById("evento-update-form").reset();
            } else {
                let errorMessage = data && data.message ? data.message : "No se pudo actualizar el evento.";
                alert(`❌ Error: ${errorMessage}`);
            }

            console.log("📢 Respuesta del servidor:", data);

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            alert("🚨 Error de conexión.");
        }
    });
});


// Eliminar evento
document.getElementById("evento-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevenir comportamiento por defecto

    const id = document.getElementById("evento-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de tenerlo en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${EVENTO_API_BASE_URL}events/${id}`, {  // Usamos EVENTO_API_BASE_URL
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Evento con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("evento-delete-form").reset();
            alert("✅ Evento eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el evento"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
