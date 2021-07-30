<template>
  <div class="home">
      <AppFormInput label="Usuario" />
      <AppButton>Iniciar Sesión</AppButton>
  </div>
</template>

<script>

import AppButton from "../components/AppButton";
import AppFormInput from "../components/form/AppFormInput";

export default {
  name: 'Home',
  components: {
      AppFormInput,
      AppButton
  }
}
</script>
