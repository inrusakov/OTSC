export default class Utils {
    static getDate(date: Date) {
        return (
            [
                date.getFullYear(),
                this.padTo2Digits(date.getMonth() + 1),
                this.padTo2Digits(date.getDate()),

            ].join('-') + ' ' +
            [
                Utils.getHours(date),
                Utils.getMinutes(date)
            ].join(':')
        )
    }

    static padTo2Digits(num: number) {
        return num.toString().padStart(2, '0');
    }

    static getHours(date: Date) {
        if(date.getHours() < 10){
            return '0'+date.getHours();
        } else {
            return date.getHours();
        }
    }

    static getMinutes(date: Date) {
        if(date.getMinutes() < 10){
            return '0'+date.getMinutes();
        } else {
            return date.getMinutes();
        }
    }

}