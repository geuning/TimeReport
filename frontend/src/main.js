import { createApp} from 'vue'
import App from './App.vue'
import './index.css'
import router from './router.js'

/* import the fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* import specific icons */
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { create } from 'lodash';


/* add icons to the library */
library.add(faUserSecret)



const app = createApp(App)
app.use(router).component('font-awesome-icon', FontAwesomeIcon).mount('#app')

// createApp(App).use(router).component('font-awesome-icon', FontAwesomeIcon).mount('#app')

