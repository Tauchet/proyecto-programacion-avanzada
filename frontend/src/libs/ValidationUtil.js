function isValidateEmail(mail) {
    return /^\w+([\\.-]?\w+)*@\w+([\\.-]?\w+)*(\.\w{2,3})+$/.test(mail);
}

function isEmpty(errors, key) {
    return errors[key] === null || errors[key] === undefined;
}

export default {

    validateIfNotEmpty(errors, form, key) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (value === null || value === undefined || value.length === 0) {
                errors[key] = "¡Este campo no puede estar vacio!";
            }
        }
    },

    validateIfDigits(errors, form, key, min, max) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (min > String(value).length || String(value).length > max) {
                errors[key] = `¡Este campo debe tener entre ${min} a ${max} digitos!`;
            }
        }
    },

    validateIfPasswordsEquals(errors, form, key, otherKey) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (value !== form[otherKey]) {
                errors[key] = "¡Las contraseñas no coinciden!";
                if (isEmpty(errors, otherKey)) {
                    errors[otherKey] = "¡La contraseña no coincide!";
                }
            }
        }
    },

    validateIfEmail(errors, form, key) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (!isValidateEmail(value)) {
                errors[key] = "¡El correo es invalido!";
            }
        }
    },

    validateIfNotContainsSpaces(errors, form, key) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (value.indexOf(' ') >= 0) {
                errors[key] = "¡Este campo no puede tener espacios!";
            }
        }
    },

    validateIfExistsEmail(errors, form, key) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (value.indexOf('@') >= 0 && !isValidateEmail(value)) {
                errors[key] = "¡El correo es invalido!";
            }
        }
    },

    validateIfExistsLength(errors, form, key, number, title) {
        if (isEmpty(errors, key)) {
            const value = form[key];
            if (value.length < number) {
                errors[key] = "¡Debe existir al menos un " + title + "!";
            }
        }
    }

}