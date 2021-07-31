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
    }

}