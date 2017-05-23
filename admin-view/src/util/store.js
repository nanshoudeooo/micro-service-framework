import settings from '@/config';

/**
 * 对H5的本地存储简单的封装
 */
class Store {
    constructor(storage) {
        this.store = storage;
        this.prefix = settings.gbs.db_prefix;
    }
    set(key, value) {
        try {
            value = JSON.stringify(value);
        } catch (e) {
            value = value;
        }
     
        this.store.setItem(this.prefix + key, value);
    }
    get(key) {
        if (!key) {
            throw new Error('没有找到key。');
            return;
        }
        if (typeof key === 'object') {
            throw new Error('key不能是一个对象。');
            return;
        }
        var value = this.store.getItem(this.prefix + key);
        if (value !== null) {
            try {
                value = JSON.parse(value);
            } catch (e) {
                value = value;
            }
        }

        return value;
    }
    remove(key) {
        this.store.removeItem(this.prefix + key);
    }
}
export default {
    sessionStore:new Store(window.sessionStorage),
    localStore:new Store(window.localStorage)
}