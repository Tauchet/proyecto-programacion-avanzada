<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">

</style>

<script>
export default {
  async created() {
    const { data } = await this.$axios.get("general");
    this.$store.dispatch('load', data);
  }
}
</script>
