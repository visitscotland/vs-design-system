import {
    required,
    email,
    minLength,
    maxLength,
} from 'vuelidate/lib/validators';

const validateFormElementMixin = {
    data() {
        return {
            inputVal: this.value,
        };
    },
    computed: {
        isRequired() {
            if (typeof required !== 'undefined' && 'required' in this.validationRules) {
                return true;
            }

            return false;
        },
        /**
         * set rules object for validation
         * needed because `required`, `email` and other values
         * can't be key value pairs
         */
        rules() {
            let rulesObj = {
            };

            // eslint-disable-next-line
            for (const [key, value] of Object.entries(this.validationRules)) {
                // rules have to be either a function defined by
                // https://vuelidate-next.netlify.app/validators.html
                if (key === 'required') {
                    rulesObj = {
                        ...rulesObj,
                        required,
                    };
                } else if (key === 'email') {
                    rulesObj = {
                        ...rulesObj,
                        email,
                    };
                } else if (key === 'minLength') {
                    rulesObj = {
                        ...rulesObj,
                        minLength: minLength(value),
                    };
                } else if (key === 'maxLength') {
                    rulesObj = {
                        ...rulesObj,
                        maxLength: maxLength(value),
                    };
                }
            }

            if (typeof rulesObj !== 'undefined') {
                return {
                    inputVal: rulesObj,
                };
            }

            return {
            };
        },
        errorsList() {
            const errorsArray = [];
            const rulesKeys = Object.keys(this.rules.inputVal);

            rulesKeys.forEach((key) => {
                if (!this.$v.inputVal[key]) {
                    errorsArray.push(key);
                }
            });

            return errorsArray;
        },
    },
    methods: {
        /**
         * manually run validation and emit to parent
         */
        manualValidate() {
            if ('required' in this.rules.inputVal && !this.inputVal) {
                if (this.errorsList.indexOf('required') === -1) {
                    this.errorsList.push('required');
                }
            } else {
                for (let i = 0; i < this.errorsList.length; i++) {
                    if (this.errorsList[i] === 'required') {
                        this.errorsList.splice(i, 1);
                    }
                }
            }

            this.$emit('status-update', {
                field: this.fieldName,
                value: this.inputVal,
                errors: this.errorsList,
            });
        },
        /**
         * Emit status of input - for automatic updating
         */
        emitStatus() {
            setTimeout(() => {
                if ('required' in this.rules.inputVal && !this.inputVal) {
                    if (this.errorsList.indexOf('required') === -1) {
                        this.errorsList.push('required');
                    }
                } else {
                    for (let i = 0; i < this.errorsList.length; i++) {
                        if (this.errorsList[i] === 'required') {
                            this.errorsList.splice(i, 1);
                        }
                    }
                }

                this.$emit('status-update', {
                    field: this.fieldName,
                    value: this.inputVal,
                    errors: this.errorsList,
                });
                this.touched = true;
                this.$v.$touch();
            }, 50);
        },
    },
};

export default validateFormElementMixin;
