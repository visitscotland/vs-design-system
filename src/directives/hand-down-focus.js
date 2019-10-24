import { get, isFunction } from "lodash"

/**
 * When an element with this directive is focused on, the
 * directive passes focus to the element's first child. 
 */

export default {
    inserted(el) {
        el.onfocus = () => {
            const child = get(el, "childNodes[0]")
        
            if (isFunction(child.focus)) {
              child.focus()
            }
        }
    }
}