<template>
    <div>
      <Head/>
       <router-view></router-view>
    </div>
</template>
<script>
import Head from "@/components/Head";
import Store from '@/store'
export default {
    name: "",
    components: {
        Head
    },
    data() {
        return {};
    },
    created() {
         Store.commit("updateHeadActive",1)
    },
    methods: {
    }
};
</script>
<style>

</style>