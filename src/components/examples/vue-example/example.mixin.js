import BCard from "bootstrap-vue/es/components/card/card"
import BCardText from "bootstrap-vue/es/components/card/card-text"
import { BFormCheckbox } from "bootstrap-vue/es/components/form-checkbox"
import VsButton from "../../elements/button"
import store from "./example.store"

export default {
  status: "prototype",
  release: "0.0.1",
  components: {
    VsButton,
    BCard,
    BCardText,
    BFormCheckbox,
  },
  store,
  data() {
    return {
      showAlert: true,
    }
  },
  computed: {
    count() {
      return this.$store.state.example.count
    },
  },
  methods: {
    increment() {
      let message = this.showAlert
        ? "Incrementing count from " + this.count + " to " + (this.count + 1)
        : false

      this.$store.dispatch("example/increment", message)
    },
  },
}
