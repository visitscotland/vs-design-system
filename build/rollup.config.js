import path from 'path';
import vue from 'rollup-plugin-vue';
import alias from '@rollup/plugin-alias';
import commonjs from '@rollup/plugin-commonjs';
import resolve from '@rollup/plugin-node-resolve';
import replace from '@rollup/plugin-replace';
import json from '@rollup/plugin-json';
import minimist from 'minimist';
import postcss from 'rollup-plugin-postcss';

function pathResolve(dir) {
    return path.join(__dirname, '..', dir);
}

const argv = minimist(process.argv.slice(2));

const PATH_SRC = pathResolve('src');
const PATH_NODE_MODULES = pathResolve('node_modules');

const baseConfig = {
    input: 'src/package.esm.js',
    plugins: {
        preVue: [
            alias({
                entries: [
                    {
                        find: '@',
                        replacement: pathResolve('src'),
                    },
                    {
                        find: '@components',
                        replacement: pathResolve('src/components'),
                    },
                    {
                        find: '@docs',
                        replacement: pathResolve('src/docs'),
                    },
                    {
                        find: '@images',
                        replacement: pathResolve('src/images'),
                    },
                    {
                        find: 'vue$',
                        replacement: pathResolve('vue/dist/vue.esm.js'),
                    },
                    {
                        find: 'bootstrap-vue$',
                        replacement: pathResolve('bootstrap-vue/src/index.js'),
                    },
                ],
            }),
        ],
        replace: {
            preventAssignment: true,
            'process.env.NODE_ENV': JSON.stringify('production'),
        },
        vue: {
            css: true,
            data: {
                scss: () => '@import "@/styles/resources.scss";',
            },
            style: {
                preprocessOptions: {
                    scss: {
                        importer: [
                            (url) => ({
                                file: url
                                    .replace(/^~/, `${PATH_NODE_MODULES}/`)
                                    .replace(/^@/, PATH_SRC),
                            }),
                        ],
                    },
                },
            },
            preventAssignment: true,
            template: {
                isProduction: true,
            },
        },
        postVue: [
            resolve({
                extensions: ['.js', '.jsx', '.ts', '.tsx', '.vue'],
                preferBuiltins: false,
            }),
            commonjs(),
            json(),
            postcss({
                plugins: [],
            }),
        ],
    },
};

const external = [
    'vue',
];

const buildFormats = [];
if (!argv.format || argv.format === 'es') {
    const esConfig = {
        ...baseConfig,
        external,
        output: {
            file: 'dist/vs-component-library.esm.js',
            format: 'esm',
            exports: 'named',
        },
        plugins: [
            replace(baseConfig.plugins.replace),
            ...baseConfig.plugins.preVue,
            vue(baseConfig.plugins.vue),
            ...baseConfig.plugins.postVue,
        ],
    };
    buildFormats.push(esConfig);
}

export default buildFormats;
