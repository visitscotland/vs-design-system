import { shallowMount } from '@vue/test-utils';
import VsSocialShareItem from '../SocialShareItem';

const url = 'https://www.visitscotland.com';
const title = 'VisitScotland';

const factoryShallowMount = (propsData) => shallowMount(VsSocialShareItem, {
    propsData: {
        ...propsData,
        name: 'facebook',
        linkText: 'Facebook',
    },
    provide: {
        pageUrl: url,
        pageTitle: title,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsSocialShareItem', () => {
    it('should render a component with the data-test attribute `vs-social-share-item`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-social-share-item');
    });

    describe(':props', () => {
        it('should render share link with text `Facebook`', () => {
            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');

            expect(shareLink.text()).toBe('Facebook');
        });

        it('should render the correct icon and URL when `name` is `facebook`', () => {
            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');
            const shareIcon = shareItem.find('vsicon-stub');

            expect(shareLink.attributes('href')).toBe('https://www.facebook.com/sharer/sharer.php?u=https://www.visitscotland.com&t=VisitScotland');
            expect(shareIcon.attributes('name')).toBe('facebook');
            expect(shareIcon.attributes('customcolour')).toBe('#1877f2');
        });

        it('should render the correct icon and URL when `name` is `whatsapp`', async() => {
            await wrapper.setProps({
                name: 'whatsapp',
            });

            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');
            const shareSvg = shareItem.find('vssvg-stub');

            expect(shareLink.attributes('href')).toBe('https://wa.me/?text=VisitScotland%20-%20https://www.visitscotland.com');
            expect(shareSvg.attributes('path')).toBe('whatsapp');
        });

        it('should render the correct icon and URL when `name` is `twitter`', async() => {
            await wrapper.setProps({
                name: 'twitter',
            });

            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');
            const shareIcon = shareItem.find('vsicon-stub');

            expect(shareLink.attributes('href')).toBe('https://twitter.com/intent/tweet?text=VisitScotland&url=https://www.visitscotland.com');
            expect(shareIcon.attributes('name')).toBe('twitter');
            expect(shareIcon.attributes('customcolour')).toBe('#08A0E9');
        });

        it('should render the correct icon and URL when `name` is `pinterest`', async() => {
            await wrapper.setProps({
                name: 'pinterest',
            });

            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');
            const shareIcon = shareItem.find('vsicon-stub');

            expect(shareLink.attributes('href')).toBe('https://www.pinterest.com/pin/create/button/?url=https://www.visitscotland.com');
            expect(shareIcon.attributes('name')).toBe('pinterest');
            expect(shareIcon.attributes('customcolour')).toBe('#E60023');
        });

        it('should render the correct icon and URL when `name` is `email`', async() => {
            await wrapper.setProps({
                name: 'email',
            });

            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');
            const shareIcon = shareItem.find('vsicon-stub');

            expect(shareLink.attributes('href')).toBe('mailto:?body=VisitScotland%20-%20https://www.visitscotland.com&subject=VisitScotland');
            expect(shareIcon.attributes('name')).toBe('email');
            expect(shareIcon.attributes('customcolour')).toBe('#000000');
        });

        it('should render the correct icon and URL when `name` is `link`', async() => {
            await wrapper.setProps({
                name: 'link',
            });

            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');
            const shareIcon = shareItem.find('vsicon-stub');

            expect(shareLink.attributes('href')).toBe('#');
            expect(shareIcon.attributes('name')).toBe('link');
            expect(shareIcon.attributes('customcolour')).toBe('#000000');
        });

        it('should accept a `pageUrl` property injected by the parent component', () => {
            /* eslint-disable no-underscore-dangle */
            expect(wrapper.vm._provided.pageUrl).toBe(url);
        });

        it('should accept a `pageTitle` property injected by the parent component', () => {
            /* eslint-disable no-underscore-dangle */
            expect(wrapper.vm._provided.pageTitle).toBe(title);
        });

        it('should render `linkCopiedText` when link is copied', async() => {
            await wrapper.setProps({
                name: 'link',
                linkCopiedText: 'Link copied!',
            });

            await wrapper.setData({
                show: true,
            });

            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');

            expect(shareLink.text()).toBe('Link copied!');
        });
    });

    describe(':methods', () => {
        it('should copy text when copy to clipboard link is clicked', async() => {
            await wrapper.setProps({
                name: 'link',
            });

            document.execCommand = jest.fn();
            const shareItem = wrapper.find('[data-test="vs-social-share-item"]');
            const shareLink = shareItem.find('.vs-social-share-item__link');

            shareLink.trigger('click');
            expect(document.execCommand).toHaveBeenCalledWith('copy');
        });
    });
});
