package org.equiposeis.huellitasaventureras.dataModels

class UsuarioCliente(
    var mascotas_alta: Int = 0,
    foto_perfil: String = "",
    id_cliente: String = "",
    nombre: String = "",
    genero: Int = 3,
    edad: Int = 0,
    numero_telefonico: Long = 0L,
    domicilio: String = "",
    correo_electronico: String = "",
    tipo_usuario: Int
) : Persona(
    foto_perfil,
    id_cliente,
    nombre,
    genero,
    edad,
    numero_telefonico,
    domicilio,
    correo_electronico,
    tipo_usuario
)
