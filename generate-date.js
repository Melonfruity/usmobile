const { MongoClient } = require('mongodb');
const bcrypt = require('bcrypt');
const moment = require('moment');

async function run() {
    const client = new MongoClient('mongodb+srv://usmobileassessment:CmH8NgxuBepMvSLG@cluster0.q4gnw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0'); // Replace with your MongoDB URI
    await client.connect();
    
    const db = client.db('dev'); // Replace with your database name
    const billingCyclesCollection = db.collection('billing_cycle');
    const dailyUsagesCollection = db.collection('daily_usage');
    const usersCollection = db.collection('users');
    
    password = await bcrypt.hash('password123', 10);

    // Sample user data
    const users = [
        { firstName: 'John', lastName: 'Doe', email: 'john.doe@example.com', password: await bcrypt.hash('password123', 10) },
        { firstName: 'Jane', lastName: 'Smith', email: 'jane.smith@example.com', password: await bcrypt.hash('password123', 10) },
        { firstName: 'Alice', lastName: 'Johnson', email: 'alice.johnson@example.com', password: await bcrypt.hash('password123', 10) }
    ];

    // Insert users and create billing cycles and daily usages
    for (const user of users) {
        const result = await usersCollection.insertOne(user);
        const userId = result.insertedId;
        const mdn = Math.floor(Math.random() * 10000000000);

        // Define billing cycles with at least one cycle having today's date
        const today = moment();
        const startDate1 = today.subtract(30, 'days').toDate(); // Cycle 1 starts 30 days ago
        const endDate1 = today.add(30, 'days').toDate(); // Cycle 1 ends in 30 days
        const startDate2 = today.subtract(15, 'days').toDate(); // Cycle 2 starts 15 days ago
        const endDate2 = today.add(15, 'days').toDate(); // Cycle 2 ends in 15 days

        const billingCycles = [
            { mdn, userId: userId.toString(), startDate: startDate1, endDate: endDate1 },
            { mdn, userId: userId.toString(), startDate: startDate2, endDate: endDate2 }
        ];

        // Insert billing cycles
        const billingCycleResults = await billingCyclesCollection.insertMany(billingCycles);

        // Create daily usages for each billing cycle
        for (const cycle of billingCycleResults.insertedIds) {
            const cycleStartDate = billingCycles[cycle - 1].startDate;
            const cycleEndDate = billingCycles[cycle - 1].endDate;

            // Generate 5 daily usages within the billing cycle range
            for (let i = 0; i < 5; i++) {
                const usageDate = moment(cycleStartDate).add(i, 'days').toDate();
                const dailyUsage = {
                    mdn,
                    userId: userId.toString(),
                    usageDate: usageDate,
                    usageInMb: Math.floor(Math.random() * 100) + 1 // Random usage between 1-100 MB
                };

                await dailyUsagesCollection.insertOne(dailyUsage);
            }
        }
    }

    await client.close();
}

run().catch(console.error);