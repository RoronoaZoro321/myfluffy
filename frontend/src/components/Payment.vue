<template>
	<div class="min-h-screen bg-[#FFF3EB] flex flex-col items-center p-6">
		<!-- Title -->
		<h2 class="text-2xl font-semibold mb-4">Payment Details</h2>

		<!-- Payment details box -->
		<div class="w-full max-w-md bg-white shadow-md rounded-lg p-6">
			<!-- Reward Amount -->
			<div
				v-if="notification.reward_amount_to_pay"
				class="flex justify-center items-center flex-col mb-8"
			>
				<img
					src="../assets/payment.jpg"
					alt="home pic"
					class="mb-5 w-80"
				/>
				<p class="text-gray-800">Reward Amount</p>
				<p class="text-2xl font-bold font-gray-900">
					${{ notification.reward_amount_to_pay }}
				</p>
			</div>
			<p v-else class="text-gray-800 mb-4">
				Reward Amount: Not Available
			</p>

			<!-- Owner's Name and Pet Information -->
			<div v-if="notificationOwner">
				<p class="text-gray-800">You: {{ notificationOwner.name }}</p>
			</div>

			<p class="text-slate-500">Transfer to</p>

			<div v-if="notificationSender">
				<p class="text-gray-800 mb-4">
					{{ notificationSender.name }}
				</p>
			</div>

			<!-- Confirm Payment Button -->
			<button
				class="py-3 px-6 text-sm font-medium text-white bg-blue-600 rounded-lg hover:bg-blue-700"
				@click="confirmPayment"
			>
				Confirm Payment
			</button>
		</div>
	</div>
</template>

<script>
import axios from 'axios';

export default {
	name: 'payment',
	data() {
		return {
			notificationId: this.$route.params.id, // Get notification ID from route
			notification: {},
			notificationOwner: {},
			notificationSender: {},
			reward: null,
		};
	},
	mounted() {
		this.fetchPaymentDetails();
	},
	methods: {
		async fetchPaymentDetails() {
			try {
				const response = await axios.get(
					`http://localhost:8080/notifications/${this.notificationId}`,
					{
						withCredentials: true,
					}
				);

				const data = response.data;
				console.log(data);
				this.notification = data; // Store the notification data
				this.notificationOwner = data.notificationOwner; // Store the notification owner details
				this.notificationSender = data.notificationSender;
				this.reward = data.reward_amount_to_pay;
			} catch (error) {
				console.error('Error fetching payment details:', error);
			}
		},
		async confirmPayment() {
			// Logic for confirming payment goes here
			console.log(
				'Payment confirmed for Notification ID:',
				this.notificationId
			);
			// Redirect or perform any other actions as needed after payment confirmation

			const transaction = {
				senderId: this.notificationOwner.id,
				receiverId: this.notificationSender.id,
				amount: this.reward,
			};

			try {
				const response = await axios.put(
					`http://localhost:8080/payments/update-balance`,
					transaction,
					{
						withCredentials: true,
					}
				);

				const data = response.data;
				this.$router.push('/mainpage');
			} catch (error) {
				console.error('Error fetching payment details:', error);
			}
		},
	},
};
</script>
