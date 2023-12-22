// URL de la API que deseas consumir
const apiUrl = 'http://localhost:8080/CineWebBackend/resources/user';
document.getElementById('correo-txt-user').value = 'mjlop123365@mail.com';
document.getElementById('contraseña-txt-user').value = '123456789ll';
document.getElementById('nombre-txt-user').value = 'mjlop123365dachakucgkUA';

const buttonGet=document.getElementById('button-form')
const imgMenuAction=document.getElementById('img-menu-action')
const loginContainer=document.getElementById("login-container--main")

imgMenuAction.addEventListener("click",desplegarMenu)
buttonGet.addEventListener("click",login)

function desplegarMenu() {
    if (loginContainer.style.display !== 'none') {
        loginContainer.style.display = 'none';
    }else{
        loginContainer.style.display = 'flex';

    }
    
}

function login() {
    const apiUrl = 'http://localhost:8080/CineWebBackend/resources/user/Created';

   
        const correo = document.getElementById('correo-txt-user').value;
        const contraseña = document.getElementById('contraseña-txt-user').value;
        const nombre=document.getElementById('nombre-txt-user').value
        
        console.log("nombre es: " + (nombre !== null && nombre !== ''));
        console.log("contraseña es: "+contraseña!=null && contraseña !== '');
        console.log("correo es: "+correo!=null && correo !== '');
        const userData = { nombreUsuario: nombre, contrasena: contraseña, email: correo };


        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            
        };
        fetch(apiUrl+'?email=' + correo + '&contrasena=' + contraseña, requestOptions)
            .then(response => {
            if (!response.ok) {
                throw new Error(`Error en la solicitud: ${response.status}`);
            }
            return response.json();
        })
            .then(data => {
                console.log('Respuesta del servidor:', data);
                // Realiza acciones adicionales según la respuesta del servidor
            })
            .catch(error => console.error('Error al realizar la solicitud:', error));
    }
