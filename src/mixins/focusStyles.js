export default {
  props: {
    /**
     * Alternative style of the emphasis on focus, hover and active.
     * Default is inset outline.
     * `underline`
     */
    focusStyle: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(underline)/)
      },
    },
    /**
     * Alternative focus colour. Default is light gray.
     * `pink, white, black`
     */
    focusColour: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(pink|white|black)/)
      },
    },
  },
  computed: {
    focusStyleClass() {
      return this.focusStyle ? "vs-focus-style-" + this.focusStyle : null
    },
    focusColourClass() {
      return this.focusColour ? "vs-focus-colour-" + this.focusColour : null
    },
  },
}
