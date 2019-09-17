<template>
    <div class="loading">
    </div>
</template>
<script>
export default {
    data() {
        return {};
    },
    methods: {}
};
</script>
<style scoped>
    .loading{
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0;
        top: 0;
        background: rgba(255, 255, 255, .9) url("../assets/images/loading.gif") center center no-repeat; 
        z-index: 20;
    }
</style>