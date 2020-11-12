import { shallowMount, mount } from '@vue/test-utils';

import VsButton from '../Button';

const slotText = 'Button text';

const factoryShallowMount = (propsData) => shallowMount(VsButton, {
    slots: {
        default: slotText,
    },
    propsData: {
        ...propsData,
    },
    attrs: {
        'test-attribute': 'test-value',
    },
});

const factoryMount = (propsData) => mount(VsButton, {
    propsData: {
        ...propsData,
    },
});

const testIcon = 'food';

describe('VsButton', () => {
    it('should render a bbutton-stub', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.is('bbutton-stub')).toBe(true);
    });

    describe(':props', () => {
        it(':href - should accept and render a `href` property', () => {
            const wrapper = factoryShallowMount({
                href: 'https://www.visitscotland.com',
            });

            expect(wrapper.attributes('href')).toBe('https://www.visitscotland.com');
        });

        it(':tabindex - should accept and render a `tabindex` property', () => {
            const wrapper = factoryShallowMount({
                tabindex: '2',
            });

            expect(wrapper.attributes('tabindex')).toBe('2');
        });

        it(':variant - should accept and render variants as props', () => {
            const testVariant = 'success';
            const wrapper = factoryMount({
                variant: testVariant,
            });

            expect(wrapper.classes(`btn-${testVariant}`)).toBe(true);
        });

        it(':background - should accept and render a `background` property', () => {
            const testBgColor = 'white';
            const wrapper = factoryMount({
                background: testBgColor,
            });

            expect(wrapper.classes(`btn-bg-${testBgColor}`)).toBe(true);
        });

        it(':size - should accept and render a `size` property', () => {
            const testSize = 'sm';
            const wrapper = factoryShallowMount({
                size: testSize,
            });

            expect(wrapper.attributes('size')).toBe(testSize);
        });

        describe(':icon', () => {
            it('should *NOT* render an icon if `icon` property is not passed', () => {
                const wrapper = factoryShallowMount();
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.exists()).toBe(false);
            });

            it('should *NOT* render the VsButton with a `vs-button-with-icon` class if icon prop is *NOT* provided', () => {
                const wrapper = factoryMount();

                expect(wrapper.classes('vs-button-with-icon')).toBe(false);
            });

            it('should render the VsButton with a `vs-button-with-icon` class if icon prop is provided', () => {
                const wrapper = factoryMount({
                    icon: testIcon,
                });

                expect(wrapper.classes('vs-button-with-icon')).toBe(true);
            });

            it('should accept and render an `icon` property', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.attributes('name')).toBe(testIcon);
            });

            it('size should be `xs` if button size is set to `sm`', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                    size: 'sm',
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.attributes('size')).toBe('xs');
            });

            it('size should be `sm` if button size is set to `md`', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                    size: 'md',
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.attributes('size')).toBe('sm');
            });

            it('size should be `md` if button size is set to `lg`', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                    size: 'lg',
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.attributes('size')).toBe('md');
            });

            it('orientation should be `down` if `iconOrientation` is set to `down`', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                    iconOrientation: 'down',
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.attributes('orientation')).toBe('down');
            });

            it('should set an `mr-2` class if `iconOnly` is not truthy', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                    iconOnly: false,
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.classes('mr-2')).toBe(true);
            });

            it('should *NOT* set an `mr-2` class if `iconOnly` is truthy', () => {
                const wrapper = factoryShallowMount({
                    icon: testIcon,
                    iconOnly: true,
                });
                const iconStub = wrapper.find('vsicon-stub');

                expect(iconStub.classes('mr-2')).toBe(false);
            });
        });


        describe(':animate', () => {
            it('should set a `btn-animate` class if `animate` is set to `false`', () => {
                const wrapper = factoryMount({
                    animate: true,
                });

                expect(wrapper.classes('btn-animate')).toBe(true);
            });

            it('should *NOT* set a `btn-animate` class if `animate` is set to `false`', () => {
                const wrapper = factoryMount({
                    animate: false,
                });

                expect(wrapper.classes('btn-animate')).toBe(false);
            });

            it('should set a `bubble` class on `click` if `animate` is set to `true`', () => {
                const wrapper = factoryMount({
                    animate: true,
                });

                wrapper.trigger('click');
                expect(wrapper.classes('bubble')).toBe(true);
            });

            it('should remove the `bubble` class 1 second after the click', () => {
                const wrapper = factoryMount({
                    animate: true,
                });

                wrapper.trigger('click');

                expect(wrapper.classes('bubble')).toBe(true);
                setTimeout(() => {
                    expect(wrapper.classes('bubble')).toBe(false);
                }, 1000);
            });

            it('should *NOT* set a `bubble` class on `click` if `animate` is set to `false`', () => {
                const wrapper = factoryMount({
                    animate: false,
                });

                wrapper.trigger('click');
                expect(wrapper.classes('bubble')).toBe(false);
            });
        });
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.text()).toContain(slotText);
        });
    });
});
