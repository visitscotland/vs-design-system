<template>
    <div>
        <div
            v-for="(thisType, index1) in sortedTypes"
            :key="index1"
            class="token-type"
        >
            <table class="rsg--table">
                <thead>
                    <tr>
                        <th>Token Name</th>
                        <th>Value</th>
                        <th>Category</th>
                    </tr>
                </thead>
                <tbody>
                    <tr
                        v-once
                        v-for="(token, index2) in filterBy('type', thisType)"
                        :key="index2"
                        class="token"
                    >
                        <td v-if="token.name">
                            ${{ token.name.replace(/_/g, '-') }}
                        </td>
                        <td v-else>
                            N/A
                        </td>
                        <td v-if="token.value">
                            <div
                                v-if="token.type === 'color'"
                                class="example color"
                                :style="{ backgroundColor: token.value }"
                            />
                            <div
                                v-if="token.category === 'border-radius'"
                                class="example border-radius"
                                :style="{ borderRadius: token.value }"
                            />
                            <div
                                v-if="token.category === 'box-shadow'"
                                class="example box-shadow"
                                :style="{ boxShadow: token.value }"
                            />
                            <div
                                v-if="token.type === 'color'"
                                class="type"
                            >
                                {{
                                    token.originalValue
                                }}
                            </div>
                            <div
                                v-else
                                class="type"
                            >
                                {{ token.value }}
                            </div>
                        </td>
                        <td v-else>
                            N/A
                        </td>
                        <td v-if="token.category">
                            {{ token.category }}
                        </td>
                        <td v-else>
                            N/A
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import {
    orderBy, filter, map, uniq, castArray,
} from 'lodash';
import designTokens from '@/assets/tokens/tokens.raw.json';

/**
 * A list of available tokens in Vue Design System. Use these tokens in place
 * of hard-coded values in order to maintain a scalable and consistent system.
 * To edit these tokens and add more, see
 * [/src/tokens/](https://github.com/viljamis/vue-design-system/blob/master/src/tokens).
 */
export default {
    name: 'Tokens',
    props: {
        types: {
            type: [String, Array],
            default: null,
        },
        typeHeadingElement: {
            type: String,
            default: 'h2',
            validator: (value) => value.match(/(h1|h2|h3|h4|h5|h6)/),
        },
    },
    data() {
        return {
            tokens: this.orderData(designTokens.props),
        };
    },
    computed: {
        sortedTypes() {
            if (this.types) {
                return castArray(this.types);
            }

            return orderBy(uniq(map(this.tokens, 'type')));
        },
    },
    methods: {
        orderData(data) {
            const byName = orderBy(data, 'name', 'asc');
            const byCategoryAndName = orderBy(byName, 'category');
            return byCategoryAndName;
        },
        filterBy(filterOn, term) {
            return filter(this.tokens, [filterOn, term]);
        },
    },
};
</script>

<style lang="scss" scoped>
@import '../../styles/docs.tokens.scss';
@import '../../styles/docs.mixins.scss';

/* STYLES
--------------------------------------------- */

.token-type {
    .type {
        max-width: calc(100% - #{$space-m});
        float: left;
    }
    .example {
        @include inline-space($space-xs);
        border-radius: $radius-default;
        background: $docs-color-white;
        box-shadow: $shadow-s-inset, $shadow-s-inset, $shadow-s-inset;
        width: $space-s;
        height: $space-s;
        float: left;
    }
}
</style>

<docs>
  ```jsx
  <tokens/>

  <tokens type="color" />
  ```
</docs>
