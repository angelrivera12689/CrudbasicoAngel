

//registrar asistente
document.getElementById("asistente-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let nombre = document.getElementById("asistente-nombre").value;
    let email = document.getElementById("asistente-email").value;
    let phone = document.getElementById("asistente-phone").value;

    // Crear el objeto JSON
    let bodyContent = JSON.stringify({
        "name": nombre,
        "email": email,
        "phone": phone
    });

    try {
        let response = await fetch("http://localhost:8080/api/v1/assistants/", {
            method: "POST",
            headers: {
                "Accept": "*/*",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.text(); 

        if (response.ok) {
            document.getElementById("mensaje").innerText = "Registro exitoso 🎉";
            document.getElementById("asistente-form").reset(); // Limpiar formulario
        } else {
            document.getElementById("mensaje").innerText = "Error al registrar 😢";
        }

        console.log(data); // Para depuración en la consola

    } catch (error) {
        console.error("Error en la petición:", error);
        document.getElementById("mensaje").innerText = "Error de conexión 😢";
    }
});

//registrar categoria
document.getElementById("registro-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let bodyContent = JSON.stringify({
        "name": document.getElementById("registro-nombre").value,
        "description": document.getElementById("registro-descripcion").value
    });

    let response = await fetch("http://localhost:8080/api/v1/category-events/", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: bodyContent
    });

    let data = await response.text();
    console.log(data);
    alert("Categoría de evento registrada con éxito");
});


// resgistrar empleado
document.getElementById("persona-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let bodyContent = JSON.stringify({
        "firstName": document.getElementById("persona-firstName").value,
        "lastName": document.getElementById("persona-lastName").value,
        "address": document.getElementById("persona-address").value,
        "phoneNumber": document.getElementById("persona-phoneNumber").value
    });

    let response = await fetch("http://localhost:8080/api/v1/employee/", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: bodyContent
    });

    let data = await response.text();
    console.log(data);
    alert("Persona registrada con éxito");
});

//registrar evento 
document.getElementById("evento-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    let eventName = document.getElementById("evento-nombre").value.trim();
    let description = document.getElementById("evento-descripcion").value.trim();
    let location = document.getElementById("evento-ubicacion").value.trim();
    let categoryId = parseInt(document.getElementById("evento-categoria").value.trim());
    let imageUrl = document.getElementById("evento-imagen").value.trim();

    if (!eventName || !description || !location || !imageUrl || isNaN(categoryId)) {
        alert("Todos los campos son obligatorios, y la categoría debe ser un número válido.");
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
        let response = await fetch("http://localhost:8080/api/v1/events/", {
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
    }
});



//registro organizar
document.getElementById("organizer-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let name = document.getElementById("organizador-nombre").value.trim();
    let phone = document.getElementById("organizador-telefono").value.trim();
    let email = document.getElementById("organizador-email").value.trim();

    // Validar que los campos no estén vacíos
    if (!name || !phone || !email) {
        alert("Todos los campos son obligatorios.");
        return;
    }

    let bodyContent = JSON.stringify({
        name: name,
        phone: phone,
        email: email
    });

    let headersList = {
        "Accept": "*/*",
        "Content-Type": "application/json"
    };

    try {
        let response = await fetch("http://localhost:8080/api/v1/organizer/", {
            method: "POST",
            body: bodyContent,
            headers: headersList
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud: " + response.statusText);
        }

        let data = await response.json();
        console.log("Organizador registrado:", data);
        alert("Organizador registrado con éxito");
        document.getElementById("organizer-form").reset(); // Limpia el formulario
    } catch (error) {
        console.error("Error al registrar el organizador:", error);
        alert("Error al registrar el organizador.");
    }
});
//registro reseña
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
        let response = await fetch("http://localhost:8080/api/v1/reviews/", {
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

// registrar ticket
document.getElementById("ticket-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let eventId = parseInt(document.getElementById("ticket-event-id").value.trim());
    let assistantId = parseInt(document.getElementById("ticket-assistant-id").value.trim());
    let price = parseFloat(document.getElementById("ticket-price").value.trim());
    let seatNumber = document.getElementById("ticket-seat-number").value.trim();

    // Validar que los campos no estén vacíos y sean correctos
    if (isNaN(eventId) || isNaN(assistantId) || isNaN(price) || !seatNumber) {
        alert("Todos los campos son obligatorios y deben ser válidos.");
        return;
    }

    let bodyContent = JSON.stringify({
        eventId: eventId,
        assistantId: assistantId,
        price: price,
        seatNumber: seatNumber
    });

    let headersList = {
        "Accept": "*/*",
        "User-Agent": "Thunder Client (https://www.thunderclient.com)",
        "Content-Type": "application/json"
    };

    try {
        let response = await fetch("http://localhost:8080/api/v1/tickets/", {
            method: "POST",
            body: bodyContent,
            headers: headersList
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud: " + response.statusText);
        }

        let data = await response.json();
        console.log("Ticket registrado:", data);
        alert("Ticket registrado con éxito");
        document.getElementById("ticket-form").reset(); // Limpia el formulario
    } catch (error) {
        console.error("Error al registrar el ticket:", error);
        alert("Error al registrar el ticket.");
    }
});


