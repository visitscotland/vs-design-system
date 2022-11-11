<template>
    <VsRow>
        <VsCol
            cols="12"
            sm="6"
            md="4"
            xl="3"
            v-for="path in icons"
            :key="path"
        >
            <div class="card d-flex align-items-center">
                <VsIcon
                    v-once
                    :name="path"
                    size="xl"
                    class="my-4"
                />
                <pre>{{ path }}</pre>
            </div>
        </VsCol>
    </VsRow>
</template>

<script>
import VsIcon from '@components/elements/icon/';
import { VsRow, VsCol } from '@components/elements/grid';
import axios from 'axios';

export default {
    name: 'Icons',
    components: {
        VsIcon,
        VsCol,
        VsRow,
    },
    data() {
        return {
            icons: [],
        };
    },
    mounted() {
        this.fetchIcons();
    },
    methods: {
        async fetchIcons() {
            /*
            * Query the FA Graph API to retrieve icons,
            * iterate over the uploaded icons and return
            * name for the VsIcon component render
            */
            const accessToken = await axios({
                method: 'POST',
                url: `${process.env.ICON_API_URL }/token`,
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${process.env.ICON_API_TOKEN}`,
                },
            }).then((response) => response.data.access_token);
            fetch(`${process.env.ICON_API_URL}/me`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${accessToken}`,
                },
                body: JSON.stringify({
                    query: `
                        query {
                            me {
                                kit(token:"7c48e8b3d4") {
                                    iconUploads {
                                        name
                                    }
                                }
                            }
                        }
                    `,
                }),
            })
                .then((res) => res.json())
                .then((result) => {
                    this.icons = result.data.me.kit.iconUploads.map((icon) => icon.name);
                }).catch((error) => {
                    // eslint-disable-next-line no-console
                    console.log(error);
                });
        },
    },
};

</script>

<style lang="scss" scoped>
.card {
    padding: 0.5rem 0;
    margin-bottom: 1rem;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);

    pre {
        white-space: normal;
        text-align: center;
    }
}
</style>

<docs>
  ```jsx
    <icons />
  ```
</docs>
