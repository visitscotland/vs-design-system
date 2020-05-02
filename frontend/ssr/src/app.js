
import Vue from 'vue';

import VsImageWithCaption from '../../src/components/patterns/image-with-caption/ImageWithCaption'
import VsImg from '../../src/components/elements/img/Img'
// import VsImageLocationMap from '../../src/components/patterns/image-location-map/ImageLocationMap'
// import VsButton from '../../src/components/elements/button/Button'
// import VsCol from '../../src/components/elements/layout/Col'
// import VsRow from '../../src/components/elements/layout/Row'
// import VsSvg from '../../src/components/elements/svg/Svg'
// import VsIcon from '../../src/components/elements/icon/Icon'

export const createApp = (context) =>  {

    const vueOptions = Object.assign({}, context.vueOptions, {
        components: {
            VsImageWithCaption,
            VsImg,
            // VsImageLocationMap,
            // VsButton,
            // VsCol,
            // VsRow,
            // VsSvg,
            // VsIcon,
        },
        comments: true
    });

    const app = new Vue(vueOptions);

    return app
};