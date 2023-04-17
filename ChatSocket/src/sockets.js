//Agregando logica del servidor para los clientes 

module.exports = (io) =>{

    let nickNames = [];

    io.on('connection', socket =>{
        console.log('Nuevo usuario Conectado');

        //Recoleccion de datos desde el usuario
        socket.on('enviar mensaje', (datos) =>{

            //console.log(datos);

            io.sockets.emit('nuevo mensaje', {
                msg:datos,
                username:socket.nickNames
            });

        });

        socket.on('nuevo usuario', (datos,callback) =>{
            if(nickNames.indexOf(datos) != -1){
                callback(false);
            }else{
                callback(true);
                socket.nickNames = datos;
                nickNames.push(socket.nickNames);

                io.sockets.emit('nombre usuario', nickNames);
            }
        });

        socket.on('disconnect', datos =>{
            if(!socket.nickNames){
                return;
            }else{
                nickNames.splice(nickNames.indexOf(socket.nickNames), 1);
                io.sockets.emit('nombre usuario', nickNames);
            }
        })

    })
}