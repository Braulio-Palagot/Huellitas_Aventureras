package org.equiposeis.huellitasaventureras.dataModels

class UsuarioCliente(
    var id_cliente: String = "",
    var mascotas_alta: Int = 0,
    var metodo_pago: String = "",
    nombre:String = "",
    genero:Int = 3,
    edad:Int = 0,
    numero_telefonico:Long = 0L,
    domicilio:String = "",
    correo_electronico:String = ""
): Persona(nombre, genero, edad, numero_telefonico, domicilio, correo_electronico)
