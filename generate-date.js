const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const moment = require('moment');

async function run() {
    const Schema = mongoose.Schema;
    const connectionString = 'mongodb+srv://usmobileassessment:CmH8NgxuBepMvSLG@cluster0.q4gnw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0';
    const dbName = 'dev';

    await mongoose.connect(`${connectionString}`, { dbName });
    
    const UserSchema = new Schema({
        firstName: String,
        lastName: String,
        email: String,
        password: String, 
        _class: { type: String, default: 'com.usmobile.assessment.user_service.models.User' },
        lastModifiedDate: { type: Date, default: Date.now() },
        createdDate: { type: Date, default: Date.now()}}
    );

    const DailyUsageSchema = new Schema({
        mdn: { type: String },
        userId: String,
        usageDate: { type: Date },
        usageInMb: Number,
        _class: { type: String, default: 'com.usmobile.assessment.cycle_usage_service.models.DailyUsage' },
        lastModifiedDate: { type: Date, default: Date.now() },
        createdDate: { type: Date, default: Date.now()}}
    );

    const BillingCycleSchema = new Schema({
        mdn: { type: String },
        userId: String,
        startDate: { type: Date },
        endDate: { type: Date },
        _class: { type: String, default: 'com.usmobile.assessment.cycle_usage_service.models.BillingCycle' },
        lastModifiedDate: { type: Date, default: Date.now() },
        createdDate: { type: Date, default: Date.now()}},
    );


    const UserModel = mongoose.model("User", UserSchema, 'users');
    const DailyUsageModel = mongoose.model("DailyUsage", DailyUsageSchema, 'daily_usage');
    const BillingCycleModel = mongoose.model("BillingCycleModel", BillingCycleSchema, 'billing_cycle');

    
    password = await bcrypt.hash('password123', 10);

    // Sample user data
    const users = [
        { firstName: 'John', lastName: 'Doe', email: 'john.doe@example.com', password },
        // { firstName: 'Jane', lastName: 'Smith', email: 'jane.smith@example.com', password },
        // { firstName: 'Alice', lastName: 'Johnson', email: 'alice.johnson@example.com', password }
    ];

    // Insert users and create billing cycles and daily usages
    for (const user of users) {
        const newUser = await UserModel.create(user);
        
        console.log(newUser.id)

        const mdn = (Math.floor(Math.random() * 10000000000));

        // Define billing cycles with at least one cycle having today's date
        const today = moment();

        const startDate1 = today.subtract(10, 'days').toDate(); // Cycle 1 starts 30 days ago
        const endDate1 = today.add(30, 'days').toDate(); // Cycle 1 ends in 30 days
        
        const startDate2 = today.subtract(45, 'days').toDate(); // Cycle 2 starts 15 days ago
        const endDate2 = today.subtract(15, 'days').toDate(); // Cycle 2 ends in 15 days

        const billingCycles = [
            { mdn: `${mdn.toString()}`, userId: newUser.id, startDate: startDate1, endDate: endDate1 },
            { mdn: `${mdn.toString()}`, userId: newUser.id, startDate: startDate2, endDate: endDate2 }
        ];

        // Insert billing cycles
        const newBillingCycles = await BillingCycleModel.insertMany(billingCycles);

        console.log("Billing Cycles: ", newBillingCycles);

        // Create daily usages for each billing cycle
        for (const cycle of newBillingCycles) {
          
            console.log(cycle);

            let dailyUsages = []
            // Generate 5 daily usages within the billing cycle range
            for (let i = 0; i < 5; i++) {
                const usageDate = moment(cycle.startDate).add(i, 'days').toDate();
                dailyUsages.push({
                    mdn,
                    userId: newUser.id,
                    usageDate: usageDate,
                    usageInMb: Math.floor(Math.random() * 100) + Math.random().toFixed(2)
                });
            }

            const newDailyUsages = await DailyUsageModel.insertMany(dailyUsages);
            // console.log('newDailyUsages', newDailyUsages);
        }
    }
    return
}

run().catch(console.error);