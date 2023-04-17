//Detectando usuarios conectados
$(function() {
const socket = io();
var nick = '';

    //Accedemos a los elementos del DOM

    const messageForm = $('#messages-form');
    const messageBox = $('#message');
    const chat = $('#chat');

    const nickForm = $("#nick-form");
    const nickError = $("#nick-error");
    const nickName = $("#nick-name");
    const userNames = $("#usernames");


    //Eventos

        //Enviar un mensaje al servidor
        messageForm.submit( e =>{
            //Evitamos que se recargue la pantalla:
            e.preventDefault();
            //Enviamos el evento que debe recibir el servidor:
            socket.emit('enviar mensaje', messageBox.val());
            //Limpiamos el input
            messageBox.val('');
        });

    //Obtener respuesta del servidor
    
    socket.on('nuevo mensaje', function(datos){
        //console.log(datos);

        let color = "#f4f4f4";

        if(nick == datos.username){
            color = "#9ff4c5";
        }

        chat.append(`
        <div class="msg-area mb-2 d-flex" style="background-color:${color}">
            <p class="msg"><b>${datos.username} :</b> ${datos.msg}</p>
        </div>
        `);
    });

    //Nuevo usuario

    nickForm.submit( e =>{

        e.preventDefault();

        socket.emit("nuevo usuario", nickName.val(), datos =>{
            if(datos){
                nick = nickName.val();
                $('#nick-wrap').hide();
                $('#content-wrap').show();
            }else{
                nickError.html('<div class="alert alert-danger">El usuario ya existe</div>');

            }
            nickName.val('');

        });
    });

    //Obtener el nombre de los usuarios conectados

    socket.on('nombre usuario', datos =>{
        let html = '';
        let color = '';
        let salir = '';

        for(let i = 0; i < datos.length; i++){
            if(nick == datos[i]){
                color = '#027f43';
                salir = '<a class="enlace-salir" href="/">Salir</a>';
            }else{
                color = '#000';
                salir = '';
            }
            html += `<p style="color:${color}"><i class="fas fa-user"></i> ${datos[i]} ${salir}</p>`;
        }

        userNames.html(html);

    });


})