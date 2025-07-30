import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import dict from './modules/dict'
import user from './modules/user'
import tagsView from './modules/tagsView'
import permission from './modules/permission'
import settings from './modules/settings'
import getters from './getters'
import equipment from "@/views/system/equipment/index.vue";
import trialUse from "@/views/system/trialUse/index.vue";
import trialAndTracking from "@/views/system/trialAndTracking/index.vue";
import test from "@/views/system/test/index.vue";
import review from "@/views/system/review/index.vue";
import report from "@/views/system/report/index.vue";
import project from "@/views/system/project/index.vue";
import material from "@/views/system/material/index.vue";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    dict,
    user,
    tagsView,
    permission,
    settings,
    equipment,
    material,
    project,
    report,
    review,
    test,
    trialAndTracking,
    trialUse
  },
  getters
})

export default store
