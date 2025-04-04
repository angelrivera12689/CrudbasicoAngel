
//actualizar Asitente
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("asistente-update-form").addEventListener("submit", async function(event) {
        event.preventDefault();

        let id = document.getElementById("update-asistente-id").value.trim();
        let nombre = document.getElementById("update-asistente-nombre").value.trim();
        let email = document.getElementById("update-asistente-email").value.trim();
        let phone = document.getElementById("update-asistente-phone").value.trim();

        let mensajeElemento = document.getElementById("mensaje");
        mensajeElemento.innerText = ""; // Limpiamos cualquier mensaje previo

        if (!id || !nombre || !email || !phone) {
            let mensaje = "Todos los campos son obligatorios ⚠️";
            mensajeElemento.innerText = mensaje;
            alert(mensaje); // Notificación al usuario
            return;
        }

        let bodyContent = JSON.stringify({
            "name": nombre,
            "email": email,
            "phone": phone
        });

        let url = `http://localhost:8080/api/v1/assistants/${id}`;

        try {
            let response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Accept": "*/*",
                    "Content-Type": "application/json"
                },
                body: bodyContent
            });

            let data = await response.json();
            console.log("📢 Respuesta del servidor:", data);

            if (response.ok) {
                let mensaje = "Asistente actualizado con éxito 🎉";
                mensajeElemento.innerText = mensaje;
                alert(mensaje); // Notificación al usuario
            } else {
                let mensaje = `Error: ${data.message || "No se pudo actualizar"}`;
                mensajeElemento.innerText = mensaje;
                alert(mensaje); // Notificación al usuario
            }

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            let mensaje = "Error de conexión 😢";
            mensajeElemento.innerText = mensaje;
            alert(mensaje); // Notificación al usuario
        }
    });
});

// actualizar la categoria
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("categoria-update-form").addEventListener("submit", async function (event) {
        event.preventDefault();

        let id = document.getElementById("categoria-id-update").value.trim();
        let nombre = document.getElementById("categoria-nombre-update").value.trim();
        let descripcion = document.getElementById("categoria-descripcion-update").value.trim();

        let mensajeElemento = document.getElementById("mensaje");
        mensajeElemento.innerText = ""; // Limpiamos cualquier mensaje previo

        if (!id || !nombre || !descripcion) {
            let mensaje = "Todos los campos son obligatorios ⚠️";
            mensajeElemento.innerText = mensaje;
            alert(mensaje); // Notificación al usuario
            return;
        }

        let bodyContent = JSON.stringify({
            "name": nombre,
            "description": descripcion
        });

        let url = `http://localhost:8080/api/v1/category-events/${id}`;

        try {
            let response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Accept": "*/*",
                    "Content-Type": "application/json"
                },
                body: bodyContent
            });

            let data = await response.json();
            console.log("📢 Respuesta del servidor:", data);

            if (response.ok) {
                let mensaje = "Categoría actualizada con éxito 🎉";
                mensajeElemento.innerText = mensaje;
                alert(mensaje); // Notificación al usuario
            } else {
                let mensaje = `Error: ${data.message || "No se pudo actualizar la categoría"}`;
                mensajeElemento.innerText = mensaje;
                alert(mensaje); // Notificación al usuario
            }

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            let mensaje = "Error de conexión 😢";
            mensajeElemento.innerText = mensaje;
            alert(mensaje); // Notificación al usuario
        }
    });
});

//actualizar Empleado

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("empleado-update-form").addEventListener("submit", async function (event) {
        event.preventDefault(); // Evita recargar la página

        // Capturar los valores del formulario
        let id = document.getElementById("empleado-id-update").value.trim();
        let nombre = document.getElementById("empleado-nombre-update").value.trim();
        let apellido = document.getElementById("empleado-apellido-update").value.trim();
        let direccion = document.getElementById("empleado-direccion-update").value.trim();
        let telefono = document.getElementById("empleado-telefono-update").value.trim();

        // Validar que los campos no estén vacíos
        if (!id || !nombre || !apellido || !direccion || !telefono) {
            alert("⚠️ Todos los campos son obligatorios.");
            return;
        }

        // Crear el objeto JSON
        let bodyContent = JSON.stringify({
            "firstName": nombre, 
            "lastName": apellido, 
            "address": direccion, 
            "phoneNumber": telefono
        });
        
        let url = `http://localhost:8080/api/v1/employee/${id}`;
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

            let data = await response.json();
            console.log("📢 Respuesta del servidor:", data);

            if (response.ok) {
                alert("✅ Empleado actualizado con éxito.");
                document.getElementById("empleado-update-form").reset(); // Limpiar formulario
            } else {
                alert(`❌ Error: ${data.message || "No se pudo actualizar el empleado"}`);
            }

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            alert("🚨 Error de conexión.");
        }
    });
});

//actualizar Empleado Evento

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("evento-update-form").addEventListener("submit", async function (event) {
        event.preventDefault(); // Evita recargar la página

        // Capturar los valores del formulario
        let id = document.getElementById("evento-id").value.trim();
        let nombre = document.getElementById("evento-nombre-update").value.trim();
        let descripcion = document.getElementById("evento-descripcion-update").value.trim();
        let ubicacion = document.getElementById("evento-ubicacion-update").value.trim();
        let categoria = document.getElementById("evento-categoria-update").value.trim();

        // Validar que los campos no estén vacíos
        if (!id || !nombre || !descripcion || !ubicacion || !categoria) {
            alert("⚠️ Todos los campos son obligatorios.");
            return;
        }

        // Crear el objeto JSON con los nombres correctos
        let bodyContent = JSON.stringify({
            "eventName": nombre,
            "description": descripcion,
            "location": ubicacion,
            "categoryId": parseInt(categoria) // Convertir a número si es un ID
        });

        let url = `http://localhost:8080/api/v1/events/${id}`;
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

            console.log("📢 Estado de la respuesta:", response.status);

            // Intentar convertir la respuesta a JSON
            let data;
            try {
                data = await response.json();
            } catch (jsonError) {
                console.error("⚠️ Error convirtiendo la respuesta a JSON:", jsonError);
                data = null;
            }

            if (response.ok) {
                alert("✅ Evento actualizado con éxito.");
                document.getElementById("evento-update-form").reset(); // Limpiar formulario
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

//actualziar organizador

document.getElementById("organizador-update-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let id = document.getElementById("organizador-id-update").value.trim();
    let nombre = document.getElementById("organizador-nombre-update").value.trim();
    let telefono = document.getElementById("organizador-telefono-update").value.trim();
    let email = document.getElementById("organizador-email-update").value.trim();

    // Validar que los campos no estén vacíos
    if (!id || !nombre || !telefono || !email) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    // Crear el objeto JSON para enviar
    let bodyContent = JSON.stringify({
        "name": nombre,
        "phone": telefono,
        "email": email
    });

    try {
        let response = await fetch(`http://localhost:8080/api/v1/organizer/${id}`, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.json(); // Convertir la respuesta a JSON

        if (response.ok) {
            alert("✅ Organizador actualizado con éxito 🎉");
            document.getElementById("organizador-update-form").reset(); // Limpiar formulario
        } else {
            alert(`❌ Error: ${data.message || "No se pudo actualizar el organizador"}`);
        }

        console.log("📢 Respuesta del servidor:", data); // Para depuración

    } catch (error) {
        console.error("❌ Error en la petición:", error);
        alert("❌ Error de conexión");
    }
});


//actualizar reseña

document.getElementById("reseña-update-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let id = document.getElementById("reseña-id-update").value.trim();
    let comentario = document.getElementById("reseña-comentario-update").value.trim();
    let calificacion = document.getElementById("reseña-calificacion-update").value.trim();
    let eventoId = document.getElementById("reseña-evento-id-update").value.trim();
    let asistenteId = document.getElementById("reseña-asistente-id-update").value.trim();

    // Validar que los campos no estén vacíos
    if (!id || !comentario || !calificacion || !eventoId || !asistenteId) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    // Asegurar que la calificación esté entre 1 y 5
    if (calificacion < 1 || calificacion > 5) {
        alert("La calificación debe estar entre 1 y 5 ⭐");
        return;
    }

    // Crear el objeto JSON para enviar
    let bodyContent = JSON.stringify({
        "comment": comentario,
        "rating": parseInt(calificacion),
        "eventId": parseInt(eventoId),
        "assistantId": parseInt(asistenteId)
    });

    try {
        let response = await fetch(`http://localhost:8080/api/v1/reviews/${id}`, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.json(); // Convertir la respuesta a JSON

        if (response.ok) {
            alert("✅ Reseña actualizada con éxito 🎉");
            document.getElementById("reseña-update-form").reset(); // Limpiar formulario
        } else {
            alert(`❌ Error: ${data.message || "No se pudo actualizar la reseña"}`);
        }

        console.log("📢 Respuesta del servidor:", data); // Para depuración

    } catch (error) {
        console.error("❌ Error en la petición:", error);
        alert("❌ Error de conexión");
    }
});

//actualizar Ticket

document.getElementById("ticket-update-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let id = document.getElementById("ticket-id-update").value.trim();
    let eventId = document.getElementById("ticket-event-id-update").value.trim();
    let assistantId = document.getElementById("ticket-assistant-id-update").value.trim();
    let price = document.getElementById("ticket-price-update").value.trim();
    let seatNumber = document.getElementById("ticket-seat-number-update").value.trim();

    // Validar que los campos no estén vacíos
    if (!id || !eventId || !assistantId || !price || !seatNumber) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    // Validar que el precio sea un número positivo
    if (parseFloat(price) <= 0) {
        alert("El precio debe ser mayor a 0 💰");
        return;
    }

    // Crear el objeto JSON para enviar
    let bodyContent = JSON.stringify({
        "eventId": parseInt(eventId),
        "assistantId": parseInt(assistantId),
        "price": parseFloat(price),
        "seatNumber": seatNumber
    });

    try {
        let response = await fetch(`http://localhost:8080/api/v1/tickets/${id}`, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.json(); // Convertir la respuesta a JSON

        if (response.ok) {
            alert("✅ Ticket actualizado con éxito 🎉");
            document.getElementById("ticket-update-form").reset(); // Limpiar formulario
        } else {
            alert(`❌ Error: ${data.message || "No se pudo actualizar el ticket"}`);
        }

        console.log("📢 Respuesta del servidor:", data); // Para depuración

    } catch (error) {
        console.error("❌ Error en la petición:", error);
        alert("❌ Error de conexión");
    }
});
