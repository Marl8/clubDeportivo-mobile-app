package com.example.clubdeportivo.entities

data class Usuario(
    val idUsuario: Int?,
    val username: String,
    var password: String,
    var rol: String,
    override var name: String,
    override var lastName: String,
    override var dni: String,
    override var email: String,
    override var phone: String
) : Persona(name, lastName, dni, email, phone){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Usuario) return false

        return idUsuario == other.idUsuario &&
                username == other.username &&
                password == other.password &&
                rol == other.rol &&
                name == other.name &&
                lastName == other.lastName &&
                dni == other.dni &&
                email == other.email &&
                phone == other.phone
    }

    override fun hashCode(): Int {
        return listOf(idUsuario, username, password, rol, name, lastName,
        dni, email, phone).hashCode()
    }
}
