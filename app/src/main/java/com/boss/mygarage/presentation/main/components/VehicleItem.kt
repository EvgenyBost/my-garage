package com.boss.mygarage.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boss.mygarage.R
import com.boss.mygarage.domain.model.Vehicle
import com.boss.mygarage.domain.model.VehicleColor
import com.boss.mygarage.domain.model.VehicleMetricType
import com.boss.mygarage.domain.model.getColorMetric
import com.boss.mygarage.presentation.common.mappers.toColor
import com.boss.mygarage.presentation.common.mappers.toDisplayName
import com.boss.mygarage.presentation.common.mappers.toIcon
import com.boss.mygarage.presentation.common.mappers.toVehicleColorOrNull

@Composable
fun VehicleItem(
    vehicle: Vehicle,
    onEditClick: () -> Unit,
    onCardClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick(vehicle.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Icon
            VehicleIconBox(vehicle)

            Spacer(modifier = Modifier.width(16.dp))

            // 2. Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vehicle.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text( //TODO Use for description
                    text = "description",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                vehicle.metadata.filter { it.showOnMain }.forEach { metric ->
                    val metricName = if (metric.type == VehicleMetricType.CUSTOM) metric.customName
                        ?: "" else metric.type.toDisplayName()
                    val metricValue = if (metric.type == VehicleMetricType.COLOR) {
                        metric.value.toVehicleColorOrNull()?.toDisplayName()
                            ?: VehicleColor.CUSTOM_COLOR.toDisplayName()
                    } else metric.value

                    Text(
                        text = "${metricName}: $metricValue",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            // 3. Edit button
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_vehicle_content_description),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
