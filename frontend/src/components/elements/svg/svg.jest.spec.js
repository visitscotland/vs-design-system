import { extend, trim } from 'lodash';
import { shallowMount } from '@vue/test-utils';
import svgContext from '@/utils/svg-context';
import VsSvg from './Svg';
import mockSvg from '../../../../test/unit/mocks/svgMock';

let svgPath;
let wrapper;
let mockNativeAttrs;
let extraAttrs;
let extraProps;
let expectedSvgContent;

jest.mock('@/utils/svg-context');

beforeEach(() => {
    jest.clearAllMocks();

    extraProps = {
        height: 150,
        width: 440,
        fill: 'rgb(227, 43, 199)',
    };

    mockNativeAttrs = {
        xmlns: 'http://www.w3.org/2000/svg',
        id: 'svg2',
        viewBox: '0 0 161.64 223.98',
        style: 'fill:red;border:1px solid black',
    };

    extraAttrs = {
        height: 150,
        width: 440,
        style: 'fill:rgb(227, 43, 199);border:1px solid black',
    };

    svgPath = 'whatever';

    wrapper = shallowMount(VsSvg, {
        propsData: {
            path: svgPath,
        },
    });

    expectedSvgContent = '<path id="path4" d="m75.066 166.68c13.7 6.3 20.2 7.2 34.6 4.3l-13.6 9.7-6.2 36.6-1.7-37.6zm-72.6-86.5c-2.9 4.5-3.5 7.7-0.4 12.2l47.9 68.7-9.4 62.9h86.4v-53.7l23.8-48.1c3.1-6.2 3.7-10.2 3.7-23.9l-28.5 29.5-24.1-12.4-7.3 38.9c-7.7-35.9-24.7-57.8-51.4-69.1l7.6-14.6c30.8 16.5 42.2-11.7 37.3-14l-48-23c-4-1.9-6.4-1.8-8.85 2.1zm143.3-13.6-33.3 36.7c-2.1 2.3 11.6 16.3 13.7 14.5l35.1-31.5c3-2.7-13-22.5-15.5-19.7zm-55.8 16.1c-1.8 2.4 17.5 19.4 19.2 17.5l35.7-39.6c3.7-4.1-20.1-25.4-22.55-22.05zm1.4-67.6-17.4 29.2 15.7 8.2c4 2.1 5.2 8 1.5 21.4l28-40.7c2.9-4.4-25.3-22.3-27.8-18.1zm-32.7-14.2-15.9 28.7 26.7 12.6 16.5-27.3c2.7-4.5-24.8-18.5-27.3-14z" />';
});

describe('VsSvg', () => {
    describe('computed: svg', () => {
        it('should return the contents of the svg file', () => {
            expect(wrapper.vm.svg).toBe(mockSvg);
            expect(svgContext.mock.calls.length).toBe(1);
            expect(svgContext.mock.calls[0][0]).toBe(`./${svgPath}.svg`);
        });
    });

    describe('computed: native attributes', () => {
        it('should return the atributes of the svg in the file', () => {
            expect(wrapper.vm.nativeAttrs).toEqual(mockNativeAttrs);
            expect(svgContext.mock.calls.length).toBe(1);
            expect(svgContext.mock.calls[0][0]).toBe(`./${svgPath}.svg`);
        });
    });

    describe('computed: attributes', () => {
        it('should return the native attributes', () => {
            expect(wrapper.vm.attributes).toEqual(mockNativeAttrs);
        });

        it('should add the extra attributes to the native attributes', () => {
            wrapper.setProps(extraProps);
            expect(wrapper.vm.attributes).toEqual(extend({
            }, mockNativeAttrs, extraAttrs));
        });
    });

    describe('computed: children', () => {
        it('should return the svg children', () => {
            const childrenTrimmed = trim(wrapper.vm.children.replace(/\n( )*/g, ' '));
            expect(childrenTrimmed).toBe(expectedSvgContent);
        });
    });
});
